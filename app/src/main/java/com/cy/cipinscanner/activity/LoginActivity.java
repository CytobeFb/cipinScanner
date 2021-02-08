package com.cy.cipinscanner.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cy.cipinscanner.R;
import com.cy.cipinscanner.app.CipinScannerApp;
import com.cy.cipinscanner.bean.LoginBean;
import com.cy.cipinscanner.utils.ActivityStackManager;
import com.cy.cipinscanner.utils.Constant;
import com.cy.cipinscanner.utils.OkgoUtils;
import com.cy.cipinscanner.utils.ToastUtil;
import com.cy.cipinscanner.utils.dialog.CommonDialogFragment;
import com.cy.cipinscanner.utils.dialog.DialogFragmentHelper;
import com.cy.cipinscanner.utils.dialog.OnDialogResultListener;
import com.cy.cipinscanner.utils.permission.PerUtils;
import com.cy.cipinscanner.utils.permission.PerimissionsCallback;
import com.cy.cipinscanner.utils.permission.PermissionEnum;
import com.cy.cipinscanner.utils.permission.PermissionManager;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2021/1/4 0004.
 */

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.ed_username)
    EditText edUsername;
    @Bind(R.id.ed_pwd)
    EditText edPwd;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_interface)
    TextView tvInterface;
    private Context mContext;
    //控制权限拒绝的显示此时
    private boolean showDenyFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        initView();
        mContext = this;
    }

    protected void initView() {
        if (!TextUtils.isEmpty(CipinScannerApp.spUtils.getString("userName",""))){
            edUsername.setText(CipinScannerApp.spUtils.getString("userName",""));
        }
        if (!TextUtils.isEmpty(CipinScannerApp.spUtils.getString("userPwd",""))){
            edPwd.setText(CipinScannerApp.spUtils.getString("userPwd",""));
        }
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(CipinScannerApp.spUtils.getString("host", ""))) {
                    ToastUtil.showShort("先保存接口地址");
                } else {
                    login();
                }
            }
        });

        tvInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InterfaceActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login() {
        if (TextUtils.isEmpty(edUsername.getText().toString())) {
            ToastUtil.showShort("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(edPwd.getText().toString())) {
            ToastUtil.showShort("请输入密码");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("userName", edUsername.getText().toString());
        params.put("userPwd", edPwd.getText().toString());
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("登录中");
        dialog.setMessage("请稍后...");
        OkgoUtils.postForParams(LoginActivity.this, CipinScannerApp.spUtils.getString("host", "")+Constant.URL_LOGIN, params, new StringCallback() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                dialog.show();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                dialog.dismiss();
                LoginBean loginBean = new Gson().fromJson(s, LoginBean.class);
                if (loginBean.getCode().equals("SUCCESS")) {
                    CipinScannerApp.spUtils.putString("userName",edUsername.getText().toString());
                    CipinScannerApp.spUtils.putString("userPwd",edPwd.getText().toString());
                    CipinScannerApp.spUtils.putString("uid",loginBean.getUid());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtil.showShort(loginBean.getMsg());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                ToastUtil.showShort(e.getMessage());
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermission();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showDenyFirst = true;
    }

    public void checkPermission() {

        PermissionManager
                .with(LoginActivity.this)
                .tag(1000)
                .permission(PermissionEnum.READ_EXTERNAL_STORAGE, PermissionEnum.READ_PHONE_STATE,
                        PermissionEnum.WRITE_EXTERNAL_STORAGE, PermissionEnum.CAMERA,
                        PermissionEnum.RECORD_AUDIO, PermissionEnum.READ_CONTACTS,
                        PermissionEnum.ACCESS_COARSE_LOCATION,
                        PermissionEnum.ACCESS_FINE_LOCATION,
                        PermissionEnum.WAKE_LOCK,
                        PermissionEnum.DISABLE_KEYGUARD)
                .callback(new PerimissionsCallback() {
                    @Override
                    public void onGranted(ArrayList<PermissionEnum> grantedList) {
                        //权限被允许


                    }

                    @Override
                    public void onDenied(ArrayList<PermissionEnum> deniedList) {
                        //权限被拒绝
                        PermissionDenied(deniedList);
                    }
                })
                .checkAsk();

    }

    private void PermissionDenied(final ArrayList<PermissionEnum> permissionsDenied) {
        //去设置(小米手机Mi-4c不停弹出diaglog的bug)

        if (mContext == null) {
            return;
        }
        StringBuilder msgCN = new StringBuilder();
        for (int i = 0; i < permissionsDenied.size(); i++) {

            if (i == permissionsDenied.size() - 1) {
                msgCN.append(permissionsDenied.get(i).getName_cn());
            } else {
                msgCN.append(permissionsDenied.get(i).getName_cn()).append(",");
            }
        }


        if (showDenyFirst) {
            showDenyFirst = false;
            DialogFragmentHelper.showConfirmDialog(getSupportFragmentManager(), "警告",
                    String.format(mContext.getResources().getString(R.string.permission_explain), msgCN.toString()),
                    new OnDialogResultListener<Boolean>() {
                        @Override
                        public void onResult(Boolean result) {
                            if (result) {
                                //去设置
                                PerUtils.openApplicationSettings(mContext, R.class.getPackage().getName());

                            } else {
                                ActivityStackManager.getInstance().killAllActivity();
                                finish();

                            }

                        }
                    }, false, new CommonDialogFragment.OnDialogCancelListener() {
                        @Override
                        public void onCancel() {
                            // ActivityStackManager.getInstance().killAllActivity();
                            // finish();
                        }
                    }, "设置", "退出");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }
}
