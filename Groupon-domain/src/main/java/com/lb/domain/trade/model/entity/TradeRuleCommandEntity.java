package com.lb.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团交易命令实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeRuleCommandEntity {

    /** 用户ID */
    private String userId;
    /** 活动ID */
    private Long activityId;

}
