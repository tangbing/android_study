package com.example.chapter08.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.entity.BillInfo;

import java.util.List;

public class BillListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<BillInfo> mBillInfoList;

    public BillListAdapter(Context mContext, List<BillInfo> mBillInfoList) {
        this.mContext = mContext;
        this.mBillInfoList = mBillInfoList;
    }

    @Override
    public int getCount() {
        return mBillInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBillInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBillInfoList.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill, null);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BillInfo info = mBillInfoList.get(position);
        holder.tv_date.setText(info.date);
        holder.tv_amount.setText(String.format("%s%d元", info.type == 0 ? "+" : "-", (int) info.amount));
        holder.tv_remark.setText(info.remark);
        return convertView;
    }

    public final  class ViewHolder {
        public TextView tv_date;
        public TextView tv_amount;
        public TextView tv_remark;

    }
}
