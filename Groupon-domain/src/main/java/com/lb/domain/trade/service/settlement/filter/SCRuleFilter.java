package com.lb.domain.trade.service.settlement.filter;

import com.lb.domain.trade.adapter.repository.ITradeRepository;
import com.lb.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import com.lb.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import com.lb.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import com.lb.types.design.framework.link.model2.handler.ILogicHandler;
import com.lb.types.enums.ResponseCode;
import com.lb.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SC 渠道来源过滤 - 当某个签约渠道下架后，则不会记账
 */
@Slf4j
@Service
public class SCRuleFilter implements ILogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private ITradeRepository repository;

    /**
     * SC 渠道来源过滤器 - 当渠道下架时拦截记账请求
     * <p>
     * 通过校验渠道是否在黑名单中，阻止已下架渠道的交易记账
     */
    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-渠道黑名单校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // sc 渠道黑名单拦截
        boolean intercept = repository.isSCBlackIntercept(requestParameter.getSource(), requestParameter.getChannel());
        if (intercept) {
            log.error("{}{} 渠道黑名单拦截", requestParameter.getSource(), requestParameter.getChannel());
            throw new AppException(ResponseCode.E0105);
        }

        return next(requestParameter, dynamicContext);
    }

}
