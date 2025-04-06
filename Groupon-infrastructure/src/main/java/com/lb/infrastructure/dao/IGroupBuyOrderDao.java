package com.lb.infrastructure.dao;

import com.lb.infrastructure.dao.po.GroupBuyOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户拼单
 * <p>
 * 该接口提供拼单订单的增删改查功能，包含拼团库存锁定、拼团进度查询等核心操作。
 */
@Mapper
public interface IGroupBuyOrderDao {

    /**
     * 插入拼单订单记录
     *
     * @param groupBuyOrder 拼单订单实体对象，包含拼团ID、用户信息、订单状态等关键数据
     */
    void insert(GroupBuyOrder groupBuyOrder);

    /**
     * 增加拼团锁定库存数量
     *
     * @param teamId 拼团唯一标识ID
     * @return 受影响的数据库记录行数
     */
    int updateAddLockCount(String teamId);

    /**
     * 减少拼团锁定库存数量
     *
     * @param teamId 拼团唯一标识ID
     * @return 受影响的数据库记录行数
     */
    int updateSubtractionLockCount(String teamId);

    /**
     * 查询拼团进度详情
     *
     * @param teamId 拼团唯一标识ID
     * @return 包含当前拼团人数、剩余人数、锁定库存等信息的拼团订单对象
     */
    GroupBuyOrder queryGroupBuyProgress(String teamId);

    GroupBuyOrder queryGroupBuyTeamByTeamId(String teamId);

    int updateAddCompleteCount(String teamId);

    int updateOrderStatus2COMPLETE(String teamId);

}
