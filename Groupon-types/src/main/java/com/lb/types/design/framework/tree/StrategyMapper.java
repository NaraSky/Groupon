package com.lb.types.design.framework.tree;

/**
 * 策略映射器接口，用于根据请求参数和动态上下文获取对应的策略处理器。
 *
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
public interface StrategyMapper<T, D, R> {

    /**
     * 根据请求参数和动态上下文获取对应的策略处理器。
     *
     * @param requestParameter 请求参数
     * @param dynamicContext   动态上下文信息
     * @return 对应的策略处理器实例
     * @throws Exception 策略解析或获取过程中发生的异常
     */
    StrategyHandler<T, D, R> get(T requestParameter, D dynamicContext) throws Exception;

}
