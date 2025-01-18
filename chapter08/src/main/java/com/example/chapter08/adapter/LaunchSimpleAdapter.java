package com.example.chapter08.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.chapter08.R;
import com.example.chapter08.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class LaunchSimpleAdapter extends PagerAdapter {

    public Context mContext;
    public  int[] launchImageArray;

    private List<View> mListView = new ArrayList<>();

    public LaunchSimpleAdapter(Context mContext, int[] launchImageArray) {
        this.mContext = mContext;
        this.launchImageArray = launchImageArray;

        for (int i = 0; i < launchImageArray.length; i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_launch, null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);
            iv_launch.setImageResource(launchImageArray[i]);

            for (int j = 0; j < launchImageArray.length; j++) {
                RadioButton radio = new RadioButton(mContext);
                radio.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                radio.setPadding(10, 10, 10, 10);
                rg_indicate.addView(radio);
            }

            // 当前位置的单选按钮要高亮显示，比如第二个引导页就高亮第二个单选按钮
            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);

            // 如果是最后一个引导页，则显示入口按钮，以使用用户点击按钮进入主页
            if (i == launchImageArray.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(v -> {
                    ToastUtil.show(mContext, "欢迎您开启美好生活");
                });
            }
            mListView.add(view);
        }
    }

    @Override
    public int getCount() {
        return mListView.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View item = mListView.get(position);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mListView.get(position));
    }

}
