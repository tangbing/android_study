package com.example.chapter08.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chapter08.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentPagerAdapter {
    private final int[] mImageArray;

    public LaunchImproveAdapter(@NonNull FragmentManager fm, int[] imageArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mImageArray = imageArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        int image_id = mImageArray[position];
        return LaunchFragment.newInstance( mImageArray.length, position, image_id);
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
