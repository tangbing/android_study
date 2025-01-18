package com.example.chapter08.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter08.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DynamicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DynamicFragment extends Fragment {

    public static  String TAG = "fragment";

    public static DynamicFragment newInstance(int position, int image_id, String desc) {
        DynamicFragment fragment = new DynamicFragment();
        // 把参数打包传入fragment中
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("image_id", image_id);
        args.putString("desc", desc);
        fragment.setArguments(args);
        return fragment;
    }

    private int getPosition() {
        return getArguments().getInt("position", 0);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "fragment onAttach position=" + getPosition());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "fragment onCreate position=" + getPosition());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            ImageView iv_pic = view.findViewById(R.id.iv_pic);
            TextView tv_name = view.findViewById(R.id.tv_desc);
            iv_pic.setImageResource(arguments.getInt("image_id", R.drawable.huawei));
            tv_name.setText(arguments.getString("desc"));
        }
        Log.d(TAG, "fragment onCreateView position=" + getPosition());
        return view;
    }

    // 在活动页面创建之后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "fragment onActivityCreated position=" + getPosition());
    }

    // 页面启动
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "fragment onStart position=" + getPosition());
    }

    // 界面恢复
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "fragment onResume position=" + getPosition());
    }

    // 界面暂停
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "fragment onPause position=" + getPosition());
    }

    // 界面停止
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "fragment onStop position=" + getPosition());
    }

    // 销毁碎片视图
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "fragment onDestroyView position=" + getPosition());

    }

    // 页面销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "fragment onDestroy position=" + getPosition());

    }

    // 把页面从界面撕下来
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "fragment onDetach position=" + getPosition());

    }
}