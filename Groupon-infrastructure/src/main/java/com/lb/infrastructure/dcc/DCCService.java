package com.lb.infrastructure.dcc;

import com.lb.types.annotation.DCCValue;
import org.springframework.stereotype.Service;

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

}