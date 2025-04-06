package com.lb.domain.trade.service;

import com.lb.domain.trade.model.entity.TradePaySettlementEntity;
import com.lb.domain.trade.model.entity.TradePaySuccessEntity;

public interface ITradeSettlementOrderService {

    /**
     * 营销结算
     * @param tradePaySuccessEntity 交易支付订单实体对象
     * @return 交易结算订单实体
     */
    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity);

}
