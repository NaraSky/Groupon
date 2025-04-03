package com.lb.types.design.framework.link.model1;

/**
 * 责任链模式的抽象基类，提供链式结构的默认实现
 *
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
public abstract class AbstractLogicLink<T, D, R> implements ILogicLink<T, D, R> {
    /**
     * 下一个处理节点
     */
    protected ILogicLink<T, D, R> next;

    /**
     * 设置并返回下一个处理节点（链式调用）
     *
     * @param next 要追加的下一个节点
     * @return 当前节点实例（支持链式调用）
     */
    @Override
    public ILogicLink<T, D, R> appendNext(ILogicLink<T, D, R> next) {
        this.next = next;
        return this; // 支持链式调用
    }

    /**
     * 获取当前节点的下一个处理节点
     *
     * @return 下一个节点（可能为 null）
     */
    @Override
    public ILogicLink<T, D, R> next() {
        return next;
    }

    /**
     * 抽象业务逻辑方法，子类需实现具体逻辑
     *
     * @param requestParameter 请求参数
     * @param dynamicContext   动态上下文（可传递中间结果或状态）
     * @return 处理结果
     * @throws Exception 可能抛出的异常（需由调用方处理）
     */
    @Override
    public abstract R apply(T requestParameter, D dynamicContext) throws Exception;
}
