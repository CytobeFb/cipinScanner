package com.cy.cipinscanner.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cy.cipinscanner.app.CipinScannerApp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Okgo 帮助类
 * Created by jaki on 2017/8/12.
 * compile 'com.lzy.net:okgo:2.1.4'
 */

public class OkgoUtils {

    /**
     * 初始化Okgo
     *
     * @param application
     */
    public static void initOkgo(Application application) {
        OkGo.init(application);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("enforce", Level.INFO, true)
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)
                    //如果不想让框架管理cookie（或者叫session的保持）,以下不需要
                    .setCookieStore(new MemoryCookieStore())            //cookie使用内存缓存（app退出后，cookie消失）
//                    .setCookieStore(new PersistentCookieStore())        //cookie持久化存储，如果cookie不过期，则一直有效
                    //可以设置https的证书,以下几种方案根据需要自己设置
                    .setCertificates();                                //方法一：信任所有证书,不安全有风险
//              .setCertificates(new SafeTrustManager())            //方法二：自定义信任规则，校验服务端证书
//              .setCertificates(getAssets().open("srca.cer"))      //方法三：使用预埋证书，校验服务端证书（自签名证书）
//              //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
//               .setCertificates(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"))//
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get请求 无参数
     *
     * @param context  上下文环境
     * @param url      请求的网址
     * @param callback 回调 ,返回的结果在第一个参数（String）里
     */
    public static void get(Context context, String url, AbsCallback callback) {
        OkGo.get(url)
                .tag(context)  // 请求的 tag, 主要用于取消对应的请求
                .headers("Cookie", CipinScannerApp.COOKIE_APP)
                .cacheKey("getCacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(callback);
    }

    /**
     * get请求 无参数
     *
     * @param context  上下文环境
     * @param url      请求的网址
     * @param callback 回调 ,返回的结果在第一个参数（String）里
     */
    public static void getByParams(Context context, String url, HttpParams params, AbsCallback callback) {
        OkGo.get(url)
                .tag(context)  // 请求的 tag, 主要用于取消对应的请求
                .headers("Cookie", CipinScannerApp.COOKIE_APP)
                .cacheKey("getCacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .params(params)
                .execute(callback);
    }


    /**
     * post请求 无参数
     *
     * @param context        上下文环境
     * @param url            请求的网址
     * @param stringCallback 回调 ,返回的结果在第一个参数（String）里
     */
    public static void post(Context context, String url, AbsCallback stringCallback) {
        OkGo.post(url)
                .tag(context)  // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("postCacheKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(stringCallback);
    }

    /**
     * 上传需要cookie
     *
     * @param context
     * @param url
     * @param httpParams
     * @param absCallback
     */
    public static void postByCookie(Context context, String url, HttpParams httpParams, AbsCallback absCallback) {
        OkGo.post(url)
                .tag(context)  // 请求的 tag, 主要用于取消对应的请求
                .headers("Cookie", CipinScannerApp.COOKIE_APP)
                .params(httpParams)
                .execute(absCallback);
    }


    /**
     * post请求 有参数
     *
     * @param context        上下文环境
     * @param url            请求的网址
     * @param map            参数的map集合
     * @param stringCallback 回调 ,返回的结果在第一个参数（String）里
     */
    public static void postForParams(Context context, String url, Map<String, String> map, AbsCallback stringCallback) {
        PostRequest postRequest = OkGo.post(url)
                .tag(context)
                .cacheKey("postCacheParamsKey")
                .cacheMode(CacheMode.DEFAULT)
                .params(map);
        postRequest.execute(stringCallback);
    }


    /**
     * 下载文件  不用写回调
     *
     * @param context  上下文环境
     * @param url      请求的网址
     * @param destPath 本地保存地址（文件夹名，后面不要带 /）
     * @param fileName 本地保存的文件名
     */
    public static void downloadFile(final Context context, String url, final String destPath, final String fileName) {
        OkGo.get(url)
                .tag(context)
                .execute(new FileCallback(destPath, fileName) {//文件下载时，可以指定下载的文件目录和文件名
                    /**
                     * 下载成功
                     * @param file
                     * @param call
                     * @param response
                     */
                    @Override
                    public void onSuccess(File file, Call call, Response response) {
                        Log.e("enforce", "download file success");
                        Toast.makeText(context, fileName + "下载成功，文件保存在：" + destPath, Toast.LENGTH_SHORT).show();
                    }

                    /**
                     * 更新下载进度
                     * @param currentSize   当前下载量 单位B
                     * @param totalSize     文件总的大小 单位B
                     * @param progress      当前进度，如果显示成百分比需要乘以100
                     * @param networkSpeed  当前下载的速度 字节/秒
                     */
                    @Override
                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);

                        //这里回调下载进度(该回调在主线程,可以直接更新ui)
                        //Log.e("enforce","total ->"+totalSize+",current->"+currentSize+"progress->"+progress+",netSpd->"+networkSpeed);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("enforce", "download file fail");
                    }
                });
    }


    /**
     * 下载文件  要写回调
     *
     * @param context      上下文环境
     * @param url          请求的网址
     * @param fileCallback 下载文件回调
     */
    public static void downloadFileCallback(Context context, String url, FileCallback fileCallback) {
        OkGo.get(url)
                .tag(context)
                .execute(fileCallback);
    }


    /**
     * 上传文件 多个文件
     *
     * @param context        上下文环境
     * @param url            请求的网址
     * @param files          本地文件集合
     * @param stringCallback 文件上传回调
     */
    public static void uploadFiles(Context context, String url, List<File> files, String zjqk, String mc, AbsCallback stringCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("zjqk", zjqk);
        map.put("mc", mc);
        OkGo.post(url)
                .tag(context)
                .isMultipart(true)
                .headers("Cookie", CipinScannerApp.COOKIE_APP)
                .headers("name", "upload_file")
                .params(map)
                .addFileParams("upload_file", files)
                .execute(stringCallback);
    }

    /**
     * 显示图片之前调用
     */
    public static void loadFtpFile(Context context, String url, String json, AbsCallback stringCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("submitFileJsonArray", json);
        OkGo.post(url)
                .tag(context)
                .params(map)
                .cacheKey("postCacheParamsKey")
                .cacheMode(CacheMode.DEFAULT)
                .params(map).execute(stringCallback);
    }

}
