package com.cy.cipinscanner.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cy.cipinscanner.R;
import com.cy.cipinscanner.adapter.CipinProductAdapter;
import com.cy.cipinscanner.app.CipinScannerApp;
import com.cy.cipinscanner.bean.CipinListBean;
import com.cy.cipinscanner.utils.Constant;
import com.cy.cipinscanner.utils.OkgoUtils;
import com.cy.cipinscanner.utils.ToastUtil;
import com.cy.cipinscanner.view.XListView.XListView;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2021/1/6 0006.
 */

public class KuweichaxunActivity extends BaseActivity {
    @Bind(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_right_text)
    TextView toolbarRightText;
    @Bind(R.id.ed_order)
    EditText edOrder;
    @Bind(R.id.lv_order)
    XListView lvOrder;
    private int pageIndex=0,pageSize=10;
    private CipinProductAdapter cipinProductAdapter;
    private List<CipinListBean.DataBean> cipinList;
    private Handler re_handler;

    @Override
    public int getLayoutId() {
        return R.layout.activity_kuweichaxun;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ivTitleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarTitle.setText("库位查询");
        toolbarRightText.setVisibility(View.INVISIBLE);

        edOrder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().endsWith("\n")){
                    getcipinlist();
                }
            }
        });

        re_handler = new Handler();
        lvOrder.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                re_handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex=0;
                        getcipinlist();
                        onLoad();
                    }
                }, 200);
            }

            @Override
            public void onLoadMore() {
                re_handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex+=10;
                        getmore();
                        onLoad();
                    }
                }, 200);
            }
        });
    }

    private void onLoad() {
        lvOrder.stopRefresh();
        lvOrder.stopLoadMore();
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

    public void getcipinlist() {
        cipinList=new ArrayList<>();
        if (TextUtils.isEmpty(edOrder.getText().toString())) {
            ToastUtil.showShort("请扫描订单号");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex+"");
        params.put("pageSize", pageSize+"");
        params.put("ORDER_CODE", edOrder.getText().toString().replace("\n",""));
        showDialog();
        OkgoUtils.postForParams(KuweichaxunActivity.this, CipinScannerApp.spUtils.getString("host", "")+Constant.URL_CIPINLIST, params, new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                dismissDialog();
                CipinListBean cipinListBean = new Gson().fromJson(s, CipinListBean.class);
                if (cipinListBean.isSuccess()) {
                    for (int i = 0; i < cipinListBean.getData().size(); i++) {
                        cipinList.add(cipinListBean.getData().get(i));
                    }
                    cipinProductAdapter=new CipinProductAdapter(KuweichaxunActivity.this);
                    cipinProductAdapter.setList(cipinList);
                    lvOrder.setAdapter(cipinProductAdapter);
                    cipinProductAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showShort(cipinListBean.getMessage());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                dismissDialog();
                ToastUtil.showShort("请求出错");
            }
        });
    }

    public void getmore() {
        if (TextUtils.isEmpty(edOrder.getText().toString())) {
            ToastUtil.showShort("请扫描订单号");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("pageIndex", pageIndex+"");
        params.put("pageSize", pageSize+"");
        params.put("ORDER_CODE", edOrder.getText().toString().replace("\n",""));
        showDialog();
        OkgoUtils.postForParams(KuweichaxunActivity.this, CipinScannerApp.spUtils.getString("host", "")+Constant.URL_CIPINLIST, params, new StringCallback() {

            @Override
            public void onSuccess(String s, Call call, Response response) {
                dismissDialog();
                CipinListBean cipinListBean = new Gson().fromJson(s, CipinListBean.class);
                if (cipinListBean.isSuccess()) {
                    for (int i = 0; i < cipinListBean.getData().size(); i++) {
                        cipinList.add(cipinListBean.getData().get(i));
                    }
                    cipinProductAdapter.setList(cipinList);
                    cipinProductAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showShort(cipinListBean.getMessage());
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                dismissDialog();
                ToastUtil.showShort("请求出错");
            }
        });
    }
}
