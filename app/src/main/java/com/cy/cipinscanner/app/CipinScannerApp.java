package com.cy.cipinscanner.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;


import com.cy.cipinscanner.exception.CrashHandler;
import com.cy.cipinscanner.utils.OkgoUtils;
import com.cy.cipinscanner.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: GoldMantis
 * @Date: 2017/8/1 09:58
 * @Version:
 * @Description:
 */

public class CipinScannerApp extends Application {

    private static CipinScannerApp mInstance;
    public static SPUtils spUtils = null;
    public static String COOKIE_APP = "";

    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //okgo2.0
        OkgoUtils.initOkgo(this);
        spUtils = new SPUtils(this, "enforce");
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }



    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        super.onTerminate();
    }


    public static CipinScannerApp getInstance() {
        return mInstance;
    }


    public static CipinScannerApp getAppContext() {
        return mInstance;
    }


    private List<Activity> generalCaseActivityList = new ArrayList<>();

    public void addGeneralCaseActivity(Activity activity) {
        generalCaseActivityList.add(activity);
    }

    public void finishAllGeneralCaseActivity() {
        for (Activity activity : generalCaseActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        generalCaseActivityList.clear();
    }


    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;

                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 检查当前网络是否可用的方法
     *
     * @param context
     *            传入一个上下文对象
     * @return 可用返回true 不可用返回false
     */
    public static boolean isNetworkAvailable(Context context) {
        /**
         * 获取手机所有的连接对象（包括wifi，net等连接的管理）
         */
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取networkinfo对象
            NetworkInfo[] netWorkinfo = connectivityManager.getAllNetworkInfo();
            if (netWorkinfo != null && netWorkinfo.length > 0) {
                for (int i = 0; i < netWorkinfo.length; i++) {
                    /**
                     * 判断已有的网络对象是否处于连接状态 连接状态则返回true 否则返回false
                     */
                    if (netWorkinfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
