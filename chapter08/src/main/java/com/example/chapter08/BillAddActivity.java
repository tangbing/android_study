package com.example.chapter08;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter08.database.BillDBHelper;
import com.example.chapter08.entity.BillInfo;
import com.example.chapter08.util.DateUtil;
import com.example.chapter08.util.ToastUtil;

import java.util.Calendar;

public class BillAddActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    TextView tv_date;
    Calendar calendar;

    EditText et_remark;
    EditText et_amount;
    RadioGroup rg_type;

    BillDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bill_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       TextView tv_title = findViewById(R.id.tv_title);
       TextView tv_option = findViewById(R.id.tv_option);
       ImageView iv_back = findViewById(R.id.iv_back);
       tv_date = findViewById(R.id.tv_date);
       iv_back.setOnClickListener(this);

       tv_title.setText("请填写账单");
        tv_option.setText("账单列表");
        tv_option.setOnClickListener(this);


        calendar = Calendar.getInstance();
        tv_date.setText(DateUtil.getDate(calendar));
        tv_date.setOnClickListener(this);

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        et_remark = findViewById(R.id.et_remark);
        et_amount = findViewById(R.id.et_amount);
        rg_type = findViewById(R.id.rg_type);

        mDbHelper = BillDBHelper.getInstance(this);
        mDbHelper.openReadLink();
        mDbHelper.openWriteLink();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                Log.d("tb", "onClick");
                DatePickerDialog dialog = new DatePickerDialog(this, this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
                break;
            case R.id.btn_save:
                BillInfo info = new BillInfo();
                info.date = tv_date.getText().toString();
                info.type = rg_type.getCheckedRadioButtonId() == R.id.rb_cost ? BillInfo.BILL_TYPE_COST : BillInfo.BILL_TYPE_INCOME;
                info.amount = Double.parseDouble(et_amount.getText().toString());
                info.remark = et_remark.getText().toString();

                if (mDbHelper.save(info) != -1) {
                    ToastUtil.show(this, "添加账单成功！");
                }
                break;
            case R.id.tv_option:
                Intent intent = new Intent(this, BillPagerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
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

        tv_date.setText(DateUtil.getDate(calendar));
    }

    @Override
    protected void onDestroy() {
        mDbHelper.closeLink();
        super.onDestroy();
    }
}