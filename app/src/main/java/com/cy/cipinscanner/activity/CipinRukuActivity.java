package com.cy.cipinscanner.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cy.cipinscanner.R;
import com.cy.cipinscanner.app.CipinScannerApp;
import com.cy.cipinscanner.bean.CheckBean;
import com.cy.cipinscanner.utils.Constant;
import com.cy.cipinscanner.utils.OkgoUtils;
import com.cy.cipinscanner.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2021/1/6 0006.
 */

public class CipinRukuActivity extends BaseActivity {
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @Bind(R.id.ed_order)
    EditText edOrder;
    @Bind(R.id.ed_kuwei)
    EditText edKuwei;
    @Bind(R.id.tv_ruku)
    TextView tvRuku;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cipinruku;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle.setText("返工单");
        toolbarRightText.setVisibility(View.INVISIBLE);

        edOrder.setFocusable(true);
        edOrder.setFocusableInTouchMode(true);
        edOrder.requestFocus();
        tvRuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruku();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public void ruku() {
        if (TextUtils.isEmpty(edOrder.getText().toString())) {
            ToastUtil.showShort("请扫描订单号");
            return;
        }
        if (TextUtils.isEmpty(edKuwei.getText().toString())){
            ToastUtil.showShort("请扫描库位");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid", CipinScannerApp.spUtils.getString("uid", ""));
        params.put("ORDER_CODE", edOrder.getText().toString().replace("\n",""));
        params.put("KUWEI_CODE", edKuwei.getText().toString().replace("\n",""));
        showDialog();
        OkgoUtils.postForParams(CipinRukuActivity.this, CipinScannerApp.spUtils.getString("host", "")+Constant.URL_CIPINRUKU, params, new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                dismissDialog();
                CheckBean checkBean = new Gson().fromJson(s, CheckBean.class);
                if (checkBean.isSuccess()) {
                    ToastUtil.showShort("入库成功");
                } else {
                    ToastUtil.showShort(checkBean.getMessage());
                }
            }
        });
    }
}
