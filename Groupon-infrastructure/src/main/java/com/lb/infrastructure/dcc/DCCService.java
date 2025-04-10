package com.lb.infrastructure.dcc;

import com.lb.types.annotation.DCCValue;
import com.lb.types.common.Constants;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DCCService {

    /**
     * 降级开关 0关闭、1开启
     */
    @DCCValue("downgradeSwitch:0")
    private String downgradeSwitch;

    /**
     * 切量范围配置（百分比值）
     */
    @DCCValue("cutRange:100")
    private String cutRange;

    @DCCValue("scBlacklist:s02c02")
    private String scBlacklist;

    /**
     * 获取降级开关状态
     * @return true表示开启降级
     */
    public boolean isDowngradeSwitch() {
        return "1".equals(downgradeSwitch);
    }

    /**
     * 判断用户是否在切量范围内
     * @param userId 用户ID
     * @return true表示在切量范围内
     */
    public boolean isCutRange(String userId) {
        // 计算哈希码的绝对值
        int hashCode = Math.abs(userId.hashCode());

        // 获取最后两位作为百分比判断依据
        int lastTwoDigits = hashCode % 100;

        // 判断用户哈希值是否小于等于配置的切量百分比
        return lastTwoDigits <= Integer.parseInt(cutRange);
    }

    /**
     * 判断黑名单拦截渠道，true 拦截、false 放行
     */
    public boolean isSCBlackIntercept(String source, String channel) {
        List<String> list = Arrays.asList(scBlacklist.split(Constants.SPLIT));
        return list.contains(source + channel);
    }

}