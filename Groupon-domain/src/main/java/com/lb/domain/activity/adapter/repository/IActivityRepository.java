package com.lb.domain.activity.adapter.repository;

import com.lb.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import com.lb.domain.activity.model.valobj.SkuVO;

public interface IActivityRepository {

    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String cource, String channel);

    SkuVO querySkuByGoodsId(String goodsId);

}
