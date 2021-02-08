package com.cy.cipinscanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cy.cipinscanner.R;
import com.cy.cipinscanner.bean.CipinListBean;
import java.util.List;


/**
 * Created by Administrator on 2017/8/11.
 */

public class CipinProductAdapter extends BaseAdapter {
    private List<CipinListBean.DataBean> list;
    LayoutInflater inflater;
    Context context;

    public CipinProductAdapter(Context context) {
        // TODO Auto-generated constructor stub
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void setList(List list) {
        this.list = list;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cipin, null);
            holder = new ViewHolder();
            holder.tv_kuwei= (TextView) convertView.findViewById(R.id.tv_kuwei);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_kuwei.setText(list.get(position).getKUWEI_CODE());
        return convertView;
    }

    class ViewHolder {
        private TextView tv_kuwei;
    }
}
