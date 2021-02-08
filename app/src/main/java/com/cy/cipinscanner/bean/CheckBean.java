package com.cy.cipinscanner.bean;

/**
 * Created by Administrator on 2021/1/5 0005.
 */

public class CheckBean {

    /**
     * success : true
     * message : 操作成功
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
