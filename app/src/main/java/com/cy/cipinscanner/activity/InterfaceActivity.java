package com.cy.cipinscanner.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.cy.cipinscanner.R;
import com.cy.cipinscanner.app.CipinScannerApp;
import com.cy.cipinscanner.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2021/1/4 0004.
 */

public class InterfaceActivity extends BaseActivity {
    @Bind(R.id.ed_interface)
    EditText edInterface;
    @Bind(R.id.tv_save)
    TextView tvSave;

    private String host="";

    @Override
    public int getLayoutId() {
        return R.layout.activity_interface;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CipinScannerApp.spUtils.putString("host",edInterface.getText().toString());
                ToastUtil.showShort("保存成功");
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        host = CipinScannerApp.spUtils.getString("host", "");
        if (TextUtils.isEmpty(host)){
            edInterface.setText("http://");
        }else {
            edInterface.setText(host);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
