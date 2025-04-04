package com.lb.domain.activity.service.trial.factory;

import com.lb.domain.activity.model.entity.MarketProductEntity;
import com.lb.domain.activity.model.entity.TrialBalanceEntity;
import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.lb.domain.activity.model.valobj.SkuVO;
import com.lb.domain.activity.service.trial.node.RootNode;
import com.lb.types.design.framework.tree.StrategyHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 默认活动策略工厂，负责创建策略处理器实例
 */
@Service
public class DefaultActivityStrategyFactory {

    private final RootNode rootNode;

    public DefaultActivityStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    /**
     * 获取策略处理器实例
     * @return 策略处理器对象
     */
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> strategyHandler() {
        return rootNode;
    }

    /**
     * 动态上下文对象，用于在策略链中传递附加参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DynamicContext {
        private GroupBuyActivityDiscountVO groupBuyActivityDiscountVO;
        private SkuVO skuVO;
        // 折扣价格
        private BigDecimal deductionPrice;
        // 活动可见性限制
        private boolean visible;
        // 活动可参与限制
        private boolean enable;
    }

}
