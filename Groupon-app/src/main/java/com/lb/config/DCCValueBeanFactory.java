package com.lb.config;

import com.lb.types.annotation.DCCValue;
import com.lb.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class DCCValueBeanFactory implements BeanPostProcessor {

    private static final String BASE_CONFIG_PATH = "group_buy_market_dcc_";

    private final RedissonClient redissonClient;

    private final Map<String, Object> dccObjGroup = new HashMap<>();

    public DCCValueBeanFactory(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Bean("dccTopic")
    public RTopic dccRedisTopicListener(RedissonClient redissonClient) {
        RTopic topic = redissonClient.getTopic("group_buy_market_dcc");
        topic.addListener(String.class, (charSequence, s) -> {
            // 配置变更消息解析
            String[] split = s.split(Constants.SPLIT);

            // 配置键
            String attribute = split[0];
            String key = BASE_CONFIG_PATH + attribute;
            String value = split[1];

            // 更新Redis缓存
            RBucket<String> bucket = redissonClient.getBucket(key);
            if (!bucket.isExists()) return;

            // 更新Bean对象的配置值
            Object objBean = dccObjGroup.get(key);
            if (null == objBean) return;

            // 反射设置新值
            Class<?> objBeanClass = objBean.getClass();
            if (AopUtils.isAopProxy(objBean)) {
                objBeanClass = AopUtils.getTargetClass(objBean);
            }

            try {
                Field field = objBeanClass.getDeclaredField(attribute);
                field.setAccessible(true);
                field.set(objBean, value);
                field.setAccessible(false);
                log.info("DCC 节点监听，动态设置值 {} {}", key, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return topic;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetBeanClass = bean.getClass();
        Object targetBeanObject = bean;
        if (AopUtils.isAopProxy(bean)) {
            targetBeanClass = AopUtils.getTargetClass(bean);
            targetBeanObject = AopProxyUtils.getSingletonTarget(bean);
        }

        // 处理所有带DCCValue注解的字段
        for (Field field : targetBeanClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(DCCValue.class)) continue;

            DCCValue dccValue = field.getAnnotation(DCCValue.class);
            String[] splits = dccValue.value().split(":");
            String key = BASE_CONFIG_PATH + splits[0];
            String defaultValue = splits.length == 2 ? splits[1] : null;

            // 校验必填默认值
            if (StringUtils.isBlank(defaultValue)) {
                throw new RuntimeException("配置项["+key+"]的默认值不能为空，请按格式配置：键:值");
            }

            // Redis操作：存在则取最新值，否则设置默认值
            RBucket<String> bucket = redissonClient.getBucket(key);
            String setValue = bucket.isExists() ? bucket.get() : defaultValue;
            bucket.setIfAbsent(defaultValue);

            // 反射设置初始值
            try {
                field.setAccessible(true);
                field.set(targetBeanObject, setValue);
                field.setAccessible(false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 保存Bean对象引用以便后续更新
            dccObjGroup.put(key, targetBeanObject);
        }
        return bean;
    }

}
