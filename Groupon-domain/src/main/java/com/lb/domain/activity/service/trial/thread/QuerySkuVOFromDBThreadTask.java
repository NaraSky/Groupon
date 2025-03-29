package com.lb.domain.activity.service.trial.thread;

import com.lb.domain.activity.adapter.repository.IActivityRepository;
import com.lb.domain.activity.model.valobj.SkuVO;
import com.lb.types.enums.ResponseCode;
import com.lb.types.exception.AppException;

import java.util.concurrent.Callable;

/**
 * 查询商品信息任务
 */
public class QuerySkuVOFromDBThreadTask implements Callable<SkuVO> {

    private final String goodsId;

    private final IActivityRepository activityRepository;

    public QuerySkuVOFromDBThreadTask(String goodsId, IActivityRepository activityRepository) {
        this.goodsId = goodsId;
        this.activityRepository = activityRepository;
    }

    @Override
    public SkuVO call() throws Exception {
        try {
            SkuVO skuVO = activityRepository.querySkuByGoodsId(goodsId);
            if (skuVO == null) {
                throw new AppException(ResponseCode.E0001.getCode(), "商品信息不存在");
            }
            return skuVO;
        } catch (Exception e) {
            // 记录异常日志
            System.err.println("查询商品信息失败: " + e.getMessage());
            return null;
        }
    }

}
