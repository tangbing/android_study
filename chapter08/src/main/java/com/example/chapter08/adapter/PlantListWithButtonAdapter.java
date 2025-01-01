package com.example.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter08.entity.Planet;
import com.example.chapter08.R;

import java.util.List;

public class PlantListWithButtonAdapter extends BaseAdapter {

    public Context context;

    List<Planet> planets;

    public PlantListWithButtonAdapter(Context context, List<Planet> planets) {
        this.context = context;
        this.planets = planets;
    }

    @Override
    public int getCount() {
        return planets.size();
    }

    @Override
    public Object getItem(int position) {
        return planets.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_with_button, null);
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.ll_item = convertView.findViewById(R.id.ll_item);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.btn_click = convertView.findViewById(R.id.btn_click);
            // 将视图持有者保存到转换视图当中
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Planet plant = planets.get(position);
        holder.iv_icon.setImageResource(plant.image);
        holder.tv_name.setText(plant.name);
        holder.tv_desc.setText(plant.desc);
        holder.ll_item.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        holder.btn_click.setOnClickListener(v -> {
            Toast.makeText(context, "selected " + plant.name, Toast.LENGTH_SHORT).show();
        });
        return convertView;
    }

    public final class ViewHolder {
        public LinearLayout ll_item;
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_desc;

        Button btn_click;
    }
}


