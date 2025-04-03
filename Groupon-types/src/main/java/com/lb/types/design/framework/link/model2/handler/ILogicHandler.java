package com.lb.types.design.framework.link.model2.handler;

/**
 * 逻辑处理器接口
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
    public interface ILogicHandler<T, D, R> {

    /**
     * 默认实现：继续调用下一个处理器
     * @param requestParameter 请求参数
     * @param dynamicContext 动态上下文
     * @return 默认返回null
     */
    default R next(T requestParameter, D dynamicContext) {
        return null;
    }

    /**
     * 执行处理逻辑
     * @param requestParameter 请求参数
     * @param dynamicContext 动态上下文
     * @return 处理结果（null表示继续处理）
     * @throws Exception 处理过程中可能抛出的异常
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;
}
