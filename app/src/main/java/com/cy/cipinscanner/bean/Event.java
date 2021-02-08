package com.cy.cipinscanner.bean;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/15 11:56
 * @Version: 1.0
 * @Description: EventBus的自定义事件
 */

public class Event<T> 
{
    /**
     * 
     */
    public int code;
    /**
     * 携带的需求的数据
     */
    public T data;

    public Event(int code) {
        this.code = code;
    }

    public Event() {
    }
}
