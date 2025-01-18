package com.example.chapter08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.adapter.ViewPagerAdapter;
import com.example.chapter08.util.ToastUtil;

import java.util.List;

public class ViewPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    List<GoodsInfo> GoodsInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager viewPager = findViewById(R.id.vp_content);
        GoodsInfoList = GoodsInfo.getDefaultList();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, GoodsInfoList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    // 在翻页过程中触发，该方法的第三个参数取值说明为：第一个参数表示当前页的序号
    // 第二个参数表示界面偏移的百分比，取值为 0到 1，第三个参数表示界面的偏移距离
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    // 在翻页结束后触发，position 表示当前滚动到哪一个界面
    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "scroll item: " + GoodsInfoList.get(position).name);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}