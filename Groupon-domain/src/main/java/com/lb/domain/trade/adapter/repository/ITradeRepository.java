package com.lb.domain.trade.adapter.repository;

import com.lb.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import com.lb.domain.trade.model.entity.GroupBuyActivityEntity;
import com.lb.domain.trade.model.entity.MarketPayOrderEntity;
import com.lb.domain.trade.model.valobj.GroupBuyProgressVO;

public interface ITradeRepository {

    MarketPayOrderEntity queryMarketPayOrderEntityByOutTradeNo(String userId, String outTradeNo);

    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    GroupBuyActivityEntity queryGroupBuyActivityEntityByActivityId(Long activityId);

    Integer queryOrderCountByActivityId(Long activityId, String userId);
}
