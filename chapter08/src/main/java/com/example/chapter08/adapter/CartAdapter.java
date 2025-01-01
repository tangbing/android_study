package com.example.chapter08.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;
import com.example.chapter08.entity.CartInfo;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.entity.Planet;

import java.util.List;


public class CartAdapter extends BaseAdapter {

    public Context mContext;

    public List<CartInfo> mCartList;

    public CartAdapter(Context mContext, List<CartInfo> mCartList) {
        this.mContext = mContext;
        this.mCartList = mCartList;
    }

    @Override
    public int getCount() {
        return mCartList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_card, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.iv_name = convertView.findViewById(R.id.iv_name);
            holder.iv_desc = convertView.findViewById(R.id.iv_desc);
            holder.iv_price = convertView.findViewById(R.id.iv_price);
            holder.iv_count = convertView.findViewById(R.id.iv_count);
            holder.iv_totalPrice = convertView.findViewById(R.id.iv_totalPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartInfo info = mCartList.get(position);
        holder.iv_thumb.setImageURI(Uri.parse(info.goods.picPath));
        holder.iv_name.setText(info.goods.name);
        holder.iv_desc.setText(info.goods.description);
        holder.iv_price.setText(String.valueOf((int) info.goods.price));
        holder.iv_count.setText(String.valueOf(info.count));
        holder.iv_totalPrice.setText(String.valueOf(info.count * (int) info.goods.price));
        return convertView;
    }

    public final class ViewHolder {
        public ImageView iv_thumb;
        public TextView iv_name;
        public TextView iv_desc;
        public TextView iv_price;
        public TextView iv_count;
        public TextView iv_totalPrice;
    }
}
