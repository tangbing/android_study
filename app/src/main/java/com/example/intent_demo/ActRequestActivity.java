package com.example.intent_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.intent_demo.utils.DateUtils;

import java.util.Date;

public class ActRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private static  final  String mRequest = "你睡了吗？来我家睡吧";
    TextView tv_response;
    private ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_request);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_request = findViewById(R.id.tv_request);
        tv_request.setText("待发送的消息为：" + mRequest);


        tv_response = findViewById(R.id.tv_response);

        findViewById(R.id.btn_request).setOnClickListener(this);
        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    String response_time = bundle.getString("response_time");
                    String response_content = bundle.getString("response_content");
                    String desc = String.format("receive msg: \nresponse time: %s\nresponse content:%s", response_time, response_content);
                    tv_response.setText(desc);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ActResponseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("request_time", DateUtils.getNowTime());
        bundle.putString("request_content", mRequest);
        intent.putExtras(bundle);
        register.launch(intent);
    }
}