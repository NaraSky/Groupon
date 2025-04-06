package com.lb.domain.trade.model.entity;

import com.lb.types.enums.GroupBuyOrderEnumVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团组队实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamEntity {

    /** 拼单组队ID */
    private String teamId;
    /** 活动ID */
    private Long activityId;
    /** 目标数量 */
    private Integer targetCount;
    /** 完成数量 */
    private Integer completeCount;
    /** 锁单数量 */
    private Integer lockCount;
    /** 状态（0-拼单中、1-完成、2-失败） */
    private GroupBuyOrderEnumVO status;

}
