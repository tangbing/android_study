package com.example.chapter08.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter08.entity.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<GoodsInfo> mGoodsList;
    List<ImageView> imageViewList;
    public ViewPagerAdapter(Context mContext, List<GoodsInfo> mGoodsList) {
        this.mContext = mContext;
        this.mGoodsList = mGoodsList;

        imageViewList = new ArrayList<>();
        for (GoodsInfo info : mGoodsList) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            imageView.setImageResource(info.pic);
            imageViewList.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 添加一个view到container中，而后返回一个跟这个view可以关联起来的对象，
        // 这个对象能够是view自身，也能够是其余对象，
        // 关键是在isViewFromObject可以将view和这个object关联起来
        ImageView imageView = imageViewList.get(position);
        container.addView(imageView);
        return imageView;
    }

    // 从容器中销毁指定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ImageView imageView = imageViewList.get(position);
        imageViewList.remove(imageView);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }
}
