package com.cy.cipinscanner.utils;


import com.cy.cipinscanner.app.CipinScannerApp;

/**
 * app中用到的一些常量
 * Created by jaki on 2017/8/12.
 */

public class Constant {


//    public static final String HOST = CipinScannerApp.spUtils.getString("host","");

//    public static final String URL_BASE = HOST + "/ZK_CANGKU/";

    //登录
    public static final String URL_LOGIN = "/ZK_CANGKU/" + "account/login.action";
    //次品入库
    public static final String URL_CIPINRUKU = "/ZK_CANGKU/" + "api/scanCipinRuku.action";
    //次品列表
    public static final String URL_CIPINLIST = "/ZK_CANGKU/" + "api/queryCipin.action";



}
