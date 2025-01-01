package com.example.chapter08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.chapter08.adapter.CartAdapter;
import com.example.chapter08.adapter.GoodsAdapter;
import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.util.ToastUtil;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener, GoodsAdapter.AddCartListener {

    private ShoppingDBHelper mDBHelper;
    TextView tv_count;
    private GridView gd_channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_channel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);

        tv_title.setText("手机商城");
        gd_channel = findViewById(R.id.gd_channel);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        mDBHelper = ShoppingDBHelper.getInstance(this);
        mDBHelper.openWriteLink();
        mDBHelper.openReadLink();

        showShopData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车数量，并展示
        showCartInfoTotalCount();
    }

    private void showCartInfoTotalCount() {
      int count =  mDBHelper.queryCartInfoCount();
      MyApplication.getInstance().goodsCount = count;
      tv_count.setText(String.valueOf(count));
    }

    private  void showShopData() {
        List<GoodsInfo> list = mDBHelper.queryAllGoodsInfo();
        GoodsAdapter adapter = new GoodsAdapter(this, list, this);
        gd_channel.setAdapter(adapter);
    }

    @Override
    public void addToCart(int goodsId, String goodsName) {
        mDBHelper.insertCartInfo(goodsId);
        ToastUtil.show(this, "添加购物车成功！");
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}