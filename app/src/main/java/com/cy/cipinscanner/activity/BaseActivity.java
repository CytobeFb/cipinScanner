package com.cy.cipinscanner.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SupportActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.cy.cipinscanner.R;
import com.cy.cipinscanner.bean.Event;
import com.cy.cipinscanner.utils.ActivityStackManager;
import com.cy.cipinscanner.utils.EventBusUtil;
import com.cy.cipinscanner.utils.StatusBarUtil;
import com.cy.cipinscanner.utils.permission.PermissionManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;


public abstract class BaseActivity extends SupportActivity {
  
    protected Activity mActivity;
    public View parentView;
    protected ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        ActivityStackManager.getInstance().addActivity(new WeakReference<Activity>(this));
        doBeforeSetView();

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        parentView = getLayoutInflater().inflate(getLayoutId(), null);
        setContentView(parentView);
        //兼容4.4状态栏透明'
        //activity的沉浸式状态栏
        if (isActivityFullScreen())
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary),0);
        else
            //Fragment沉浸式状态栏
            StatusBarUtil.setTranslucentForImageViewInFragment(mActivity, 0, null);
        //默认不绑定，需要绑定的子类复写isRegisterEventBus()方法，并返回true即可
        if (isRegisterEventBus()) 
        {
            EventBusUtil.register(this);
        }
        ButterKnife.bind(this);
        initDialog();
        initView(savedInstanceState);
        initData();
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected  void getBundleExtras(Bundle extras){};

    /**
     * 布局id
     * @return
     */
    public abstract int getLayoutId();
    /**
     * 初始化控件
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    private void doBeforeSetView() {
       
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
//        ButterKnife.unbind(this);
        ActivityStackManager.getInstance().removeActivity(new WeakReference<Activity>(this));

    }

    // 关闭软键盘
    public void closeKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    // 打开软键盘
    public void openKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }
    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() 
    {
        return false;
    }
    
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }
    

    /**
     * 接收到分发到事件（根据实际需求，子类复写该方法，实现相应需求即可）
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 默认是activity沉浸式，当子类有fragment需要全屏显示的时候，复写这个方法，返回false,并在子类
     * 的initView中调用StatusBarUtil.setTranslucentForImageViewInFragment(mActivity, 0, null);
     * 并且在相应的fragment添加一个状态栏高度的view，版本判断即可。
     */
    protected  boolean isActivityFullScreen()
    {
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }

    protected void initDialog(){
        dialog = new ProgressDialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    /**
     * 网络请求显示
     */
    protected void showDialog() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 网络请求消失
     */
    protected void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /********防止按钮连续点击********/
    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    protected String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(date);
    }

    protected String getTime2(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
