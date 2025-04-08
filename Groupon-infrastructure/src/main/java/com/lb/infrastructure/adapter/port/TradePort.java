package com.lb.infrastructure.adapter.port;

import com.lb.domain.trade.adapter.port.ITradePort;
import com.lb.domain.trade.model.entity.NotifyTaskEntity;
import com.lb.infrastructure.gateway.GroupBuyNotifyService;
import com.lb.infrastructure.redis.IRedisService;
import com.lb.types.enums.NotifyTaskHTTPEnumVO;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 负责处理团购业务相关的端口适配，实现 {@link ITradePort} 接口。
 */
@Service
public class TradePort implements ITradePort {

    @Resource
    private GroupBuyNotifyService groupBuyNotifyService;
    @Resource
    private IRedisService redisService;

    /**
     * 执行拼团通知任务，通过分布式锁确保同一任务在分布式环境下仅被单实例处理。
     *
     * @param notifyTask 通知任务实体，包含通知URL、参数及锁标识等必要信息
     * @return HTTP状态码字符串，SUCCESS表示成功，NULL表示锁竞争失败或无效任务
     * @throws Exception 可能抛出的通用异常（如网络异常、业务异常等）
     */
    @Override
    public String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception {
        RLock lock = redisService.getLock(notifyTask.lockKey());
        try {
            /*
             * group-buy-market 拼团服务端被部署到多台应用服务器上，可能导致多个任务同时执行。此处使用分布式锁进行抢占，避免重复执行。
             */
            if (lock.tryLock(3, 0, TimeUnit.SECONDS)) {
                try {
                    /*
                     * 如果通知URL为空或"暂无"，则直接返回成功状态码，无需执行通知。
                     */
                    if (StringUtils.isBlank(notifyTask.getNotifyUrl()) || "暂无".equals(notifyTask.getNotifyUrl())) {
                        return NotifyTaskHTTPEnumVO.SUCCESS.getCode();
                    }
                    return groupBuyNotifyService.groupBuyNotify(notifyTask.getNotifyUrl(), notifyTask.getParameterJson());
                } finally {
                    if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            return NotifyTaskHTTPEnumVO.NULL.getCode();
        } finally {
            Thread.currentThread().interrupt();
            return NotifyTaskHTTPEnumVO.NULL.getCode();
        }
    }
}
