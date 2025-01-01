package com.example.chapter08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.chapter08.adapter.CartAdapter;
import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.CartInfo;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private ShoppingDBHelper mDBHelper;
    TextView tv_count;
    private ListView lv_cart;

    private TextView tv_total_price;

    private LinearLayout ll_empty;

    private  LinearLayout ll_content;

    // 声明一个购物车中的商品信息列表
    private List<CartInfo> mCartList;

    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库
    private Map<Integer, GoodsInfo> mGoodsMap = new HashMap<>();

    CartAdapter mCartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shopping_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);

        tv_title.setText("购物车");
        ll_content = findViewById(R.id.ll_content);
        lv_cart = findViewById(R.id.lv_cart);
        ll_empty = findViewById(R.id.ll_empty);

        tv_total_price = findViewById(R.id.tv_total_price);


        mDBHelper = ShoppingDBHelper.getInstance(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCartInfoCount();
        showCart();
    }

    private void showCart() {
        // 根据商品编号查询商品商品数据库中的商品记录
        mCartList = mDBHelper.queryAllCartInfo();
        if (mCartList.size() == 0) {
//            ll_empty.setVisibility(View.VISIBLE);
            return;
        }

        for (CartInfo info : mCartList) {
            GoodsInfo goods = mDBHelper.queryGoodsInfoById(info.goodsId);
            mGoodsMap.put(info.goodsId, goods);
            info.goods = goods;
        }

        mCartAdapter = new CartAdapter(this, mCartList);
        lv_cart.setAdapter(mCartAdapter);

        lv_cart.setOnItemClickListener(this);
        lv_cart.setOnItemLongClickListener(this);
        refreshTotalPrice();
    }

    private void deleteGoods(CartInfo info) {
       MyApplication.getInstance().goodsCount -= info.count;
       // 从购物车删除商品
        CartInfo removed = null;
       mDBHelper.deleteCartInfoByGoodsId(info.goodsId);
        for (CartInfo cartInfo : mCartList) {
            if (cartInfo.goodsId == info.goodsId) {
                removed = cartInfo;
                break;
            }
        }
        mCartList.remove(removed);

        // 展示最新的商品数量
        showCount();

        ToastUtil.show(this, "已经从购物车中删除" + mGoodsMap.get(info.goodsId).name);
        mGoodsMap.remove(info.goodsId);
        // 刷新购物车中所有商品的总金额
        refreshTotalPrice();
    }

    private void showCount() {
        int goodsCount = MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(goodsCount));
        if (goodsCount == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            // 通知适配器发生了数据变化
            mCartAdapter.notifyDataSetChanged();
        } else  {
            ll_empty.setVisibility(View.GONE);
             ll_content.setVisibility(View.VISIBLE);
        }
    }

    private void refreshTotalPrice() {
        int totalPrice = 0;
        for (CartInfo info : mCartList) {
           GoodsInfo goodsInfo = mGoodsMap.get(info.goodsId);
           totalPrice += goodsInfo.price * info.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    private void showCartInfoCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_shopping_channel:
                Intent intent = new Intent(this, ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.btn_clear:
                mDBHelper.deleteALlCartInfo();
                MyApplication.getInstance().goodsCount = 0;
                showCount();
                break;
            case R.id.btn_settle:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("暂时不支持结算功能");
                builder.setNegativeButton("知道了", null);
                builder.create().show();
                break;

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        CartInfo info = mCartList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否从购物车删除" + info.goods.name + "?");
        builder.setPositiveButton("是", (dialog, which) -> {
//            从集合中移出数据
            mCartList.remove(position);
            // 通知适配器发生了数据变化
            mCartAdapter.notifyDataSetChanged();
            deleteGoods(info);
        });
        builder.setNegativeButton("否", null);
        builder.create().show();
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShoppingDetailActivity.class);
        intent.putExtra("goods_id", mCartList.get(position).goodsId);
        startActivity(intent);
    }
}