package com.lb.domain.activity.service;

import com.lb.domain.activity.model.entity.MarketProductEntity;
import com.lb.domain.activity.model.entity.TrialBalanceEntity;
import com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory;
import com.lb.types.design.framework.tree.StrategyHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory.*;

/**
 * 首页营销服务实现类，负责处理营销试算核心流程
 */
@Service
public class IndexGroupBuyMarketServiceImpl implements IIndexGroupBuyMarketService {

    @Resource
    private DefaultActivityStrategyFactory defaultActivityStrategyFactory;

    /**
     * 执行首页营销试算流程
     * @param marketProductEntity 商品营销参数实体
     * @return 试算结果对象
     */
    @Override
    public TrialBalanceEntity indexMarketTrial(MarketProductEntity marketProductEntity) throws Exception {
        StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler = defaultActivityStrategyFactory.strategyHandler();
        // 方法会调用 DefaultActivityStrategyFactory 创建的 RootNode 策略处理器。
        TrialBalanceEntity trialBalanceEntity = strategyHandler.apply(marketProductEntity, new DynamicContext());
        return trialBalanceEntity;
    }
}
