package com.lb.types.design.framework.tree;

import lombok.Getter;
import lombok.Setter;

 /**
 * 抽象策略路由类，实现了 {@link StrategyMapper} 和 {@link StrategyHandler} 接口，提供基础的策略路由逻辑。
 * 当具体策略处理器不存在时，将使用默认策略处理器进行处理。
 *
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    /**
     * 默认策略处理器，当无法获取具体策略时使用
     */
    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    /**
     * 策略路由方法，根据请求参数和动态上下文选择并执行对应的策略处理器。
     *
     * @param requestParameter 请求参数
     * @param dynamicContext 动态上下文信息
     * @return 处理结果
     * @throws Exception 策略处理过程中发生的异常
     */
    public R router(T requestParameter, D dynamicContext) throws Exception {
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        if (strategyHandler != null) {
            return strategyHandler.apply(requestParameter, dynamicContext);
        }
        return defaultStrategyHandler.apply(requestParameter, dynamicContext);
    }

}
