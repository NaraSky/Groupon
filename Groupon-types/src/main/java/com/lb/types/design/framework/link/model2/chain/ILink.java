package com.lb.types.design.framework.link.model2.chain;

public interface ILink<E> {

    /**
     * 添加元素到链表末尾
     * @param e 元素值
     * @return 总是返回true
     */
    boolean add(E e);

    /**
     * 添加元素到链表头部
     * @param e 元素值
     * @return 总是返回true
     */
    boolean addFirst(E e);

    /**
     * 添加元素到链表尾部
     * @param e 元素值
     * @return 总是返回true
     */
    boolean addLast(E e);

    /**
     * 根据对象移除元素
     * @param o 要移除的对象
     * @return 是否成功移除
     */
    boolean remove(Object o);

    /**
     * 根据索引获取元素
     * @param index 索引位置
     * @return 元素值
     * @throws IndexOutOfBoundsException 索引越界时抛出
     */
    E get(int index);

    /**
     * 打印链表信息
     */
    void printLinkList();

}
