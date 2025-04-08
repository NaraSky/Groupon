package com.lb.domain.trade.adapter.port;

import com.lb.domain.trade.model.entity.NotifyTaskEntity;

/**
 * 交易接口服务接口
 */
public interface ITradePort {

    String groupBuyNotify(NotifyTaskEntity notifyTask) throws Exception;

}
