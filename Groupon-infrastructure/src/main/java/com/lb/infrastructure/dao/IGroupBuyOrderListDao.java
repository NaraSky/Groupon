package com.lb.infrastructure.dao;

import com.lb.infrastructure.dao.po.GroupBuyOrderList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户拼单明细
 */
@Mapper
public interface IGroupBuyOrderListDao {

    /**
     * 插入用户拼单明细记录
     *
     * @param groupBuyOrderListReq 拼单明细对象，需包含用户ID、拼单组队ID、订单ID、活动ID、商品信息等
     * @note
     * 1. 自动填充创建时间(create_time)和更新时间(update_time)
     * 2. 外部交易单号(out_trade_no)需确保全局唯一，用于幂等性校验
     * 3. 状态(status)默认为0（初始锁定状态）
     */
    void insert(GroupBuyOrderList groupBuyOrderListReq);

    /**
     * 通过外部交易单号查询拼单记录（幂等性校验）
     *
     * @param groupBuyOrderListReq 查询条件对象，需包含：
     *   - 外部交易单号(outTradeNo)：唯一标识外部请求
     *   - 用户ID(userId)：确保用户身份一致性
     *   - 状态(status)：仅查询状态为0（初始锁定）的记录
     * @return 符合条件的拼单记录，若无记录返回null
     * @note 用于支付前的重复请求校验，避免重复创建订单
     */
    GroupBuyOrderList queryGroupBuyOrderRecordByOutTradeNo(GroupBuyOrderList groupBuyOrderListReq);

    Integer queryOrderCountByActivityId(GroupBuyOrderList groupBuyOrderListReq);

    int updateOrderStatus2COMPLETE(GroupBuyOrderList groupBuyOrderListReq);

    List<String> queryGroupBuyCompleteOrderOutTradeNoListByTeamId(String teamId);

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByUserId(GroupBuyOrderList groupBuyOrderListReq);

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByRandom(GroupBuyOrderList groupBuyOrderListReq);

    List<GroupBuyOrderList> queryInProgressUserGroupBuyOrderDetailListByActivityId(Long activityId);

}
