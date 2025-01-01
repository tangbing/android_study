package com.example.chapter06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.GoodsInfo;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    int goodsId = 0;
    TextView tv_count;

    ImageView iv_thumb;
    TextView tv_name ;
    TextView tv_desc;
    private ShoppingDBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

        tv_title.setText("商品详情");

        mDBHelper = ShoppingDBHelper.getInstance(this);

        iv_thumb = findViewById(R.id.iv_thumb);
        tv_name = findViewById(R.id.tv_name);
        tv_desc = findViewById(R.id.tv_desc);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showDetail();
    }

    public void showDetail() {
         int count = MyApplication.getInstance().goodsCount;
         tv_count.setText(String.valueOf(count));

         goodsId = getIntent().getIntExtra("goods_id", 0);
         if (goodsId > 0) {
            GoodsInfo goodsInfo = mDBHelper.queryGoodsInfoById(goodsId);
            iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
            tv_name.setText(goodsInfo.name);
            tv_desc.setText(goodsInfo.description);
         }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.iv_back:
                finish();
                break;
            case  R.id.btn_add:
                addCart();
                break;

            case R.id.iv_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
//                Intent intent = new Intent(ShoppingDetailActivity.class, ShoppingCartActivity.class);
                startActivity(intent);
              break;
        }
    }

    private void addCart() {
        // 插入数据库
        mDBHelper.insertCartInfo(goodsId);
       // 更新购物车数量
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));

    }
}