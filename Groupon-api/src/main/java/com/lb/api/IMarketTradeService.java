package com.lb.api;

import com.lb.api.dto.LockMarketPayOrderRequestDTO;
import com.lb.api.dto.LockMarketPayOrderResponseDTO;
import com.lb.api.dto.SettlementMarketPayOrderRequestDTO;
import com.lb.api.dto.SettlementMarketPayOrderResponseDTO;
import com.lb.api.response.Response;

public interface IMarketTradeService {

    /**
     * 营销锁单
     *
     * @param lockMarketPayOrderRequestDTO 锁单商品信息
     * @return 锁单结果信息
     */
    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

    /**
     * 营销结算
     *
     * @param settlementMarketPayOrderRequestDTO 结算商品信息
     * @return 结算结果信息
     */
    Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(SettlementMarketPayOrderRequestDTO settlementMarketPayOrderRequestDTO);


}
