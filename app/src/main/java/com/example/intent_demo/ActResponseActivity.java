package com.example.intent_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.intent_demo.utils.DateUtils;

import org.w3c.dom.Text;

public class ActResponseActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String mResponse = "我还没睡，我爸妈不在家。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_response);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView tv_request = findViewById(R.id.tv_request);
        // 从上一个页面传来的意图中获取快递包裹
        Bundle bundle = getIntent().getExtras();
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String desc = String.format("receive msg: \nresponse time: %s\nresponse content:%s", request_time, request_content);
        // 把请求消息的详情显示在文本视图上
        tv_request.setText(desc);

        findViewById(R.id.btn_response).setOnClickListener(this);

        TextView tv_response = findViewById(R.id.tv_response);
        tv_response.setText("待返回的消息为：" + mResponse);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response_time", DateUtils.getNowTime());
        bundle.putString("response_content",mResponse);
        intent.putExtras(bundle);
        // 携带意图返回上一个界面，RESULT_OK表示处理成功
        setResult(Activity.RESULT_OK, intent);
        // 结束当前的活动界面
        finish();

    }
}