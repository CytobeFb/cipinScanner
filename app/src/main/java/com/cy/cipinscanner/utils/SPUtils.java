package com.cy.cipinscanner.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

/**
 * sharedpreferences帮助类
 * Created by Jaki on 2017/2/27.
 */
public class SPUtils {
    private static final String TAG = "SPUtils";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String EDITLIST = "editlist";//功能整理

    private Gson gson=new Gson();

    /**
     * Sharedpreferences初始化函数（在Application里初始化）
     * @param context   上下文环境
     * @param spName    sharedpreferences的文件名
     */
    public SPUtils(Context context, String spName){
        sharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }


    /**
     * 写入int值
     * @param key   键
     * @param value 值
     */
    public  void putInt(String key,int value){
        editor.putInt(key,value).apply();
    }

    /**
     * 根据键获取int值，key有返回值，则返回改值，否则返回-1
     * @param key   键
     * @return  值
     */
    public int getInt(String key){
        return sharedPreferences.getInt(key,-1);
    }

    /**
     * 根据键获取int值，key有返回值，则返回改值，否则返回defaultValue
     * @param key   键
     * @param defaultValue  默认返回值值
     * @return  值
     */
    public int getInt(String key,int defaultValue){
        return sharedPreferences.getInt(key,defaultValue);
    }


    /**
     * 写入long值
     * @param key   键
     * @param value 值
     */
    public  void putLong(String key,long value){
        editor.putLong(key,value).apply();
    }

    /**
     * 根据键获取long值，key有返回值，则返回改值，否则返回-1L
     * @param key   键
     * @return  值
     */
    public long getLong(String key){
        return sharedPreferences.getLong(key,-1L);
    }

    /**
     * 根据键获取long值，key有返回值，则返回改值，否则返回defaultValue
     * @param key   键
     * @param defaultValue  默认返回值值
     * @return  值
     */
    public long getLong(String key,long defaultValue){
        return sharedPreferences.getLong(key,defaultValue);
    }

    /**
     * 写入float值
     * @param key   键
     * @param value 值
     */
    public  void putFloat(String key,float value){
        editor.putFloat(key,value).apply();
    }


    /**
     * 根据键获取float值，key有返回值，则返回改值，否则返回-1.0F
     * @param key   键
     * @return  值
     */
    public float getFloat(String key){
        return sharedPreferences.getFloat(key,-1.0F);
    }

    /**
     * 根据键获取float值，key有返回值，则返回改值，否则返回defaultValue
     * @param key   键
     * @param defaultValue  默认返回值值
     * @return  值
     */
    public float getFloat(String key,float defaultValue){
        return sharedPreferences.getFloat(key,defaultValue);
    }


    /**
     * 写入boolean值
     * @param key   键
     * @param value 值
     */
    public  void putBoolean(String key,boolean value){
        editor.putBoolean(key,value).apply();
    }

    /**
     * 根据键获取boolean值，key有返回值，则返回改值，否则返回false
     * @param key   键
     * @return  值
     */
    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    /**
     * 根据键获取float值，key有返回值，则返回改值，否则返回defaultValue
     * @param key   键
     * @param defaultValue  默认返回值值
     * @return  值
     */
    public boolean getBoolean(String key,boolean defaultValue){
        return sharedPreferences.getBoolean(key,defaultValue);
    }

    /**
     * 写入String值
     * @param key   键
     * @param value 值
     */
    public  void putString(String key,String value){
        editor.putString(key,value).commit();
    }


    /**
     * 根据键获取String值，key有返回值，则返回改值，否则返回do not get string
     * @param key   键
     * @return  值
     */
    public String getString(String key){
        return sharedPreferences.getString(key,"do not get string");
    }

    /**
     * 根据键获取String值，key有返回值，则返回改值，否则返回defaultValue
     * @param key   键
     * @param defaultValue  默认返回值值
     * @return  值
     */
    public String getString(String key,String defaultValue){
        return sharedPreferences.getString(key,defaultValue);
    }

    /**
     * 获取sharedPreferences里所有的值，以键值对存放于Map集合中
     * @return
     */
    public  Map<String, ?> getAll(){
      return sharedPreferences.getAll();
    }

    /**
     * 清除sharedPreferences中所有的记录
     */
    public void clear(){
        editor.clear().apply();
    }

    /**
     * 移除key对应的值
     * @param key  键
     */
    public void remove(String key){
         editor.remove(key).apply();
    }

    /**
     *
     * 是否包含该键
     * @param key     键
     * @return  false-->不包含；true-->包含
     */
    public boolean isContain(String key){
       return sharedPreferences.contains(key);
    }

    @CheckResult
    public boolean saveAsJsonEncode(@NonNull Object src, @NonNull String key) {
        try {
            String json = gson.toJson(src);
            String encode = new String(Base64.encode(json.getBytes(), Base64.DEFAULT));
            return editor.putString(key, encode).commit();
        } catch (Exception e) {
            Log.e(TAG, "Save [" + src.getClass().getSimpleName() + "] failed.", e);
        }
        return false;
    }

    @CheckResult
    public <T> T fromJsonEncode(@NonNull Class<T> clazz, @NonNull String key) {
        try {
            String encode = sharedPreferences.getString(key, "");
            String json = new String(Base64.decode(encode, Base64.DEFAULT));
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            Log.e(TAG, "Get [" + clazz.getSimpleName() + "] failed.", e);
        }
        return null;
    }
}
