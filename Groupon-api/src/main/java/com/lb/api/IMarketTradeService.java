package com.lb.api;

import com.lb.api.dto.LockMarketPayOrderRequestDTO;
import com.lb.api.dto.LockMarketPayOrderResponseDTO;
import com.lb.api.response.Response;

public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

}
