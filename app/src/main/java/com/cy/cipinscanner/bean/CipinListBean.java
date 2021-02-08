package com.cy.cipinscanner.bean;

import java.util.List;

/**
 * Created by Administrator on 2021/1/6 0006.
 */

public class CipinListBean {

    /**
     * pageInfo : {"pageIndex":0,"pageSize":1}
     * total : 1
     * data : [{"ID":"4028a88376c8b7ee0176c8b892c20000","KUWEI_CODE":"001","ORDER_CODE":"000111","REMARK":"","INSERT_USERID":"1-1","INSERT_USERNAME":"admin1","INSERT_TIME":"2021-01-03 22:48:28"}]
     * success : true
     */

    private PageInfoBean pageInfo;
    private int total;
    private boolean success;
    private List<DataBean> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PageInfoBean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfoBean pageInfo) {
        this.pageInfo = pageInfo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class PageInfoBean {
        /**
         * pageIndex : 0
         * pageSize : 1
         */

        private int pageIndex;
        private int pageSize;

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

    public static class DataBean {
        /**
         * ID : 4028a88376c8b7ee0176c8b892c20000
         * KUWEI_CODE : 001
         * ORDER_CODE : 000111
         * REMARK :
         * INSERT_USERID : 1-1
         * INSERT_USERNAME : admin1
         * INSERT_TIME : 2021-01-03 22:48:28
         */

        private String ID;
        private String KUWEI_CODE;
        private String ORDER_CODE;
        private String REMARK;
        private String INSERT_USERID;
        private String INSERT_USERNAME;
        private String INSERT_TIME;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getKUWEI_CODE() {
            return KUWEI_CODE;
        }

        public void setKUWEI_CODE(String KUWEI_CODE) {
            this.KUWEI_CODE = KUWEI_CODE;
        }

        public String getORDER_CODE() {
            return ORDER_CODE;
        }

        public void setORDER_CODE(String ORDER_CODE) {
            this.ORDER_CODE = ORDER_CODE;
        }

        public String getREMARK() {
            return REMARK;
        }

        public void setREMARK(String REMARK) {
            this.REMARK = REMARK;
        }

        public String getINSERT_USERID() {
            return INSERT_USERID;
        }

        public void setINSERT_USERID(String INSERT_USERID) {
            this.INSERT_USERID = INSERT_USERID;
        }

        public String getINSERT_USERNAME() {
            return INSERT_USERNAME;
        }

        public void setINSERT_USERNAME(String INSERT_USERNAME) {
            this.INSERT_USERNAME = INSERT_USERNAME;
        }

        public String getINSERT_TIME() {
            return INSERT_TIME;
        }

        public void setINSERT_TIME(String INSERT_TIME) {
            this.INSERT_TIME = INSERT_TIME;
        }
    }
}
