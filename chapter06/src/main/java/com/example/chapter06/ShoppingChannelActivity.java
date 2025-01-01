package com.example.chapter06;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.utils.ToastUtil;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private ShoppingDBHelper mDBHelper;
    TextView tv_count;
    private GridLayout gl_channel;


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
        gl_channel = findViewById(R.id.gl_channel);

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
        // 商品条目是一个线性布局，设置布局的宽度为屏幕的一半
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth /2, ViewGroup.LayoutParams.WRAP_CONTENT);
        List<GoodsInfo> list = mDBHelper.queryAllGoodsInfo();

        for (GoodsInfo info : list) {
            // 获取布局文件 item_goods.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_price = view.findViewById(R.id.tv_price);
            Button btn_add = view.findViewById(R.id.btn_add);

            Log.d("tb", "pic" + info.picPath);
            // 给控件设置值
            iv_thumb.setImageURI(Uri.parse(info.picPath));
            tv_name.setText(info.name);
            tv_price.setText(String.valueOf((int) info.price));

            // 添加到购物车
            btn_add.setOnClickListener(v -> {
                addToCart(info.id, info.name);
            });

            // 点击商品图片，跳转到商品详情页面
            iv_thumb.setOnClickListener(v -> {
                Intent intent = new Intent(this, ShoppingDetailActivity.class);
                intent.putExtra("goods_id", info.id);
                startActivity(intent);
            });

            // 把商品视图添加到网格布局
            gl_channel.addView(view, params);
        }
    }

    private void addToCart(int goodsId, String goodsName) {
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