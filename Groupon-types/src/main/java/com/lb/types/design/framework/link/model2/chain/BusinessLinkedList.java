package com.lb.types.design.framework.link.model2.chain;

import com.lb.types.design.framework.link.model2.handler.ILogicHandler;

/**
 * 责任链处理器链表实现类
 * @param <T> 请求参数类型
 * @param <D> 动态上下文类型
 * @param <R> 处理结果类型
 */
public class BusinessLinkedList<T, D, R> extends LinkedList<ILogicHandler<T, D, R>> implements ILogicHandler<T, D, R> {

    public BusinessLinkedList(String name) {
        super(name);
    }

    /**
     * 依次执行链表中的处理器
     * @param requestParameter 请求参数
     * @param dynamicContext 动态上下文
     * @return 处理结果（首个非空结果）
     * @throws Exception 处理过程中抛出的异常
     */
    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        Node<ILogicHandler<T, D, R>> current = this.first;
        do {
            ILogicHandler<T, D, R> item = current.item;
            R apply = item.apply(requestParameter, dynamicContext);
            if (null != apply) return apply;

            current = current.next;
        } while (null != current);

        return null;
    }

}
