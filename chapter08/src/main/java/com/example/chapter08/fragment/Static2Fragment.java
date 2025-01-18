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

import com.example.chapter08.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Static2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Static2Fragment extends Fragment {

    public static final String TAG = "fragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Static2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Static2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Static2Fragment newInstance(String param1, String param2) {
        Static2Fragment fragment = new Static2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // 把碎片贴到界面上
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    // 页面创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_static2, container, false);
    }

    // 在活动页面创建之后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    // 页面启动
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    // 界面恢复
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    // 界面暂停
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    // 界面停止
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    // 销毁碎片视图
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    // 页面销毁
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    // 把页面从界面撕下来
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}