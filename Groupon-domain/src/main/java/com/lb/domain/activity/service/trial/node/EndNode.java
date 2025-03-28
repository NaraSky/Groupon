package com.lb.domain.activity.service.trial.node;

import com.alibaba.fastjson.JSON;
import com.lb.domain.activity.model.entity.MarketProductEntity;
import com.lb.domain.activity.model.entity.TrialBalanceEntity;
import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.lb.domain.activity.model.valobj.SkuVO;
import com.lb.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import com.lb.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.lb.domain.activity.service.trial.factory.DefaultActivityStrategyFactory.*;

/**
 * 策略树中的结束节点，负责组装最终的试算结果。
 */
@Slf4j
@Service
public class EndNode extends AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    @Override
    public TrialBalanceEntity doApply(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        log.info("拼团商品查询试算服务-EndNode userId:{} requestParameter:{}", requestParameter.getUserId(), JSON.toJSONString(requestParameter));

        GroupBuyActivityDiscountVO groupBuyActivityDiscountVO = dynamicContext.getGroupBuyActivityDiscountVO();
        SkuVO skuVO = dynamicContext.getSkuVO();
        // 折扣价格
        BigDecimal deductionPrice = dynamicContext.getDeductionPrice();

        return TrialBalanceEntity.builder()
                .goodsId(skuVO.getGoodsId())
                .goodsName(skuVO.getGoodsName())
                .originalPrice(skuVO.getOriginalPrice())
                .deductionPrice(deductionPrice)
                .targetCount(groupBuyActivityDiscountVO.getTarget())
                .startTime(groupBuyActivityDiscountVO.getStartTime())
                .endTime(groupBuyActivityDiscountVO.getEndTime())
                .isVisible(false)
                .isEnable(false)
                .build();
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        return defaultStrategyHandler;
    }

}
