package com.example.chapter08;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.chapter08.adapter.ViewPagerAdapter;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.util.ToastUtil;

import java.util.List;

public class PagerTabActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    List<GoodsInfo> mGoodsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pager_tab);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initPagerStrip();
        initViewPager();
    }
    // 初始化翻页标签栏
    private void initViewPager() {
        PagerTabStrip pts_tab = findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        pts_tab.setTextColor(Color.BLACK);
    }

    // 初始化翻页视图
    private void initPagerStrip() {
        ViewPager vp_content = findViewById(R.id.vp_content);
        mGoodsInfoList = GoodsInfo.getDefaultList();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, mGoodsInfoList);
        vp_content.setAdapter(viewPagerAdapter);
        // 给翻页视图添加界面变更监听器
        vp_content.addOnPageChangeListener(this);
        vp_content.setCurrentItem(3);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ToastUtil.show(this, "您翻到的手机品牌是：" + mGoodsInfoList.get(position).name);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}