package com.lb.domain.activity.service.discount;

import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * 折扣计算服务
 */
public interface IDiscountCalculateService {

    BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
