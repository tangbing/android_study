package com.example.chapter08;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;

import com.example.chapter08.adapter.BillPagerAdpater;
import com.example.chapter08.database.BillDBHelper;
import com.example.chapter08.util.DateUtil;

import org.w3c.dom.Text;

import java.util.Calendar;

public class BillPagerActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    TextView tv_month;

    Calendar calendar;

    BillDBHelper mDbHelper;


    ViewPager vp_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_option = findViewById(R.id.tv_option);
        ImageView iv_back = findViewById(R.id.iv_back);
        tv_month = findViewById(R.id.tv_month);
        tv_option.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        calendar = Calendar.getInstance();
        tv_month.setText(DateUtil.getMonth(calendar));
        tv_month.setOnClickListener(this);

        tv_title.setText("账单列表");
        tv_option.setText("添加账单");


        mDbHelper = BillDBHelper.getInstance(this);
        mDbHelper.openReadLink();
        mDbHelper.openWriteLink();

        initViewPager();

    }

    private void initViewPager() {
        // 从布局视图中获取名为 pts_bill的翻页标签栏
        PagerTabStrip pts_bill = findViewById(R.id.pts_bill);
        // 设置翻页标签栏的文本大小
        pts_bill.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        vp_bill = findViewById(R.id.vp_bill);
        BillPagerAdpater adapter = new BillPagerAdpater(getSupportFragmentManager(), calendar.get(Calendar.YEAR));
        vp_bill.setAdapter(adapter);
        // 默认选中
        vp_bill.setCurrentItem(calendar.get(Calendar.MONTH));
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()) {
           case R.id.tv_month:
               Log.d("tb", "onClick");
               DatePickerDialog dialog = new DatePickerDialog(this, this,
                       calendar.get(Calendar.YEAR),
                       calendar.get(Calendar.MONTH),
                       calendar.get(Calendar.DAY_OF_MONTH)
               );
               dialog.show();
               break;

           case R.id.tv_option:
               finish();
               break;

           case R.id.iv_back:
               finish();
               break;
       }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.d("tb", "onClick" + year + month + dayOfMonth);

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        tv_month.setText(DateUtil.getMonth(calendar));

        // 设置翻页视图显示第几页
        vp_bill.setCurrentItem(month);
    }


}