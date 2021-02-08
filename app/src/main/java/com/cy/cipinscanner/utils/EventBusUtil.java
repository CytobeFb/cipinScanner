package com.cy.cipinscanner.utils;





import com.cy.cipinscanner.bean.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/5/15 11:55
 * @Version: 1.0
 * @Description: 对EventBus的封装。
 */

public class EventBusUtil 
{
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
    
}
