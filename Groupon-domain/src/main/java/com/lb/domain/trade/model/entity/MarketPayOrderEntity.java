package com.lb.domain.trade.model.entity;

import com.lb.domain.trade.model.valobj.TradeOrderStatusEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketPayOrderEntity {

    /**
     * 预购订单ID
     */
    private String orderId;
    /**
     * 折扣金额
     */
    private BigDecimal deductionPrice;
    /**
     * 交易订单状态枚举
     */
    private TradeOrderStatusEnumVO tradeOrderStatusEnumVO;

}
