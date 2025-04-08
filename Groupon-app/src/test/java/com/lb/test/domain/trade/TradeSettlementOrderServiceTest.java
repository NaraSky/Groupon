package com.lb.test.domain.trade;

import com.alibaba.fastjson.JSON;
import com.lb.domain.trade.model.entity.TradePaySettlementEntity;
import com.lb.domain.trade.model.entity.TradePaySuccessEntity;
import com.lb.domain.trade.service.ITradeSettlementOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeSettlementOrderServiceTest {

    @Resource
    private ITradeSettlementOrderService tradeSettlementOrderService;

    @Test
    public void test_settlementMarketPayOrder() throws Exception {
        TradePaySuccessEntity tradePaySuccessEntity = new TradePaySuccessEntity();
        tradePaySuccessEntity.setSource("s01");
        tradePaySuccessEntity.setChannel("c01");
        tradePaySuccessEntity.setUserId("xfg03");
        tradePaySuccessEntity.setOutTradeNo("536311764349");
        tradePaySuccessEntity.setOutTradeTime(new Date());
        TradePaySettlementEntity tradePaySettlementEntity = tradeSettlementOrderService.settlementMarketPayOrder(tradePaySuccessEntity);
        log.info("请求参数:{}", JSON.toJSONString(tradePaySuccessEntity));
        log.info("测试结果:{}", JSON.toJSONString(tradePaySettlementEntity));
    }

}
