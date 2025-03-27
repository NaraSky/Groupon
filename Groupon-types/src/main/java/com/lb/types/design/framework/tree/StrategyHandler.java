package com.lb.types.design.framework.tree;

/**
 * 策略处理器接口，定义了处理请求参数和动态上下文的方法。
 *
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
public interface StrategyHandler<T, D, R> {

    /**
     * 默认策略处理器，该处理器不会执行任何实际处理，直接返回 {@code null}。
     */
    StrategyHandler DEFAULT = (T, D) -> null;

    /**
     * 执行策略处理逻辑。
     *
     * @param requestParameter 请求参数
     * @param dynamicContext   动态上下文信息
     * @return 处理后的结果
     * @throws Exception 策略处理过程中发生的异常
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;

}
