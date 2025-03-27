package com.lb.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import com.lb.domain.activity.model.entity.MarketProductEntity;
import com.lb.domain.activity.model.entity.TrialBalanceEntity;
import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.lb.domain.activity.model.valobj.SkuVO;
import com.lb.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.lb.domain.activity.service.trial.thread.QueryGroupBuyActivityDiscountVOThreadTask;
import com.lb.domain.activity.service.trial.thread.QuerySkuVOFromDBThreadTask;
import com.lb.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.concurrent.*;

import static com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory.*;

/**
 * 策略树中的市场节点，负责异步加载数据并路由到下一个节点。
 */
@Slf4j
@Service
public class MarketNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private EndNode endNode;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 异步查询活动配置
        QueryGroupBuyActivityDiscountVOThreadTask queryGroupBuyActivityDiscountVOThreadTask =
                new QueryGroupBuyActivityDiscountVOThreadTask(requestParameter.getSource(), requestParameter.getChannel(), activityRepository);
        FutureTask<GroupBuyActivityDiscountVO> groupBuyActivityDiscountVOFutureTask = new FutureTask<>(queryGroupBuyActivityDiscountVOThreadTask);
        threadPoolExecutor.execute(groupBuyActivityDiscountVOFutureTask);

        // 异步查询商品信息 - 在实际生产中，商品有同步库或者调用接口查询。这里暂时使用DB方式查询。
        QuerySkuVOFromDBThreadTask querySkuVOFromDBThreadTask = new QuerySkuVOFromDBThreadTask(requestParameter.getGoodsId(), activityRepository);
        FutureTask<SkuVO> skuVOFutureTask = new FutureTask<>(querySkuVOFromDBThreadTask);
        threadPoolExecutor.execute(skuVOFutureTask);

        // 写入上下文 - 对于一些复杂场景，获取数据的操作，有时候会在下N个节点获取，这样前置查询数据，可以提高接口响应效率
        dynamicContext.setGroupBuyActivityDiscountVO(groupBuyActivityDiscountVOFutureTask.get(timeout, TimeUnit.MINUTES));
        dynamicContext.setSkuVO(skuVOFutureTask.get(timeout, TimeUnit.MINUTES));

        log.info("拼团商品查询试算服务-MarketNode userId:{} 异步线程加载数据「GroupBuyActivityDiscountVO、SkuVO」完成", requestParameter.getUserId());
    }

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-MarketNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        // todo 拼团优惠试算

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<MarketProductEntity, DefaultActivityStrategyFactory.DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DefaultActivityStrategyFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}
