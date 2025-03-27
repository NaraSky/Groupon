package com.lb.domain.activity.service.trial;

import com.lb.domain.activity.adapter.repository.IActivityRepository;
import com.lb.types.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * 抽象的拼团营销支持类，继承自 AbstractMultiThreadStrategyRouter。
 * 提供了异步数据加载的默认实现和资源注入。
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity>
        extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    /**
     * 异步操作的超时时间，单位为毫秒。
     */
    protected long timeout = 500;

    @Resource
    protected IActivityRepository activityRepository;

    /**
     * 异步加载数据的方法，默认实现为空。
     *
     * @param requestParameter 请求参数
     * @param dynamicContext   动态上下文
     * @throws ExecutionException   如果异步执行过程中发生异常
     * @throws InterruptedException 如果线程被中断
     * @throws TimeoutException     如果操作超时
     */
    @Override
    protected void multiThread(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 缺省的方法
    }
}
