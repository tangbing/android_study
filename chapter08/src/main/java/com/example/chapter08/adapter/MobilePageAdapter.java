package com.example.chapter08.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.fragment.DynamicFragment;

import java.util.List;

public class MobilePageAdapter extends FragmentPagerAdapter {
    private final List<GoodsInfo> mGoodsList;

    public MobilePageAdapter(@NonNull FragmentManager fm, List<GoodsInfo> goodsList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mGoodsList = goodsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        GoodsInfo info = mGoodsList.get(position);
        return DynamicFragment.newInstance(position, info.pic, info.description);
    }

    @Override
    public int getCount() {
        return mGoodsList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mGoodsList.get(position).name;
    }
}
