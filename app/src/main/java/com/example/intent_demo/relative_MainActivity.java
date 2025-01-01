package com.example.intent_demo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.intent_demo.utils.DateUtils;

public class relative_MainActivity extends AppCompatActivity  implements View.OnClickListener  {

    public TextView timeTextVie;
    public Button pushShowTimeButton;

    public Button setOnClickListenerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_relative_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        timeTextVie = findViewById(R.id.tv_showTime);

        pushShowTimeButton = findViewById(R.id.button_publish_showTime);
        pushShowTimeButton.setOnClickListener(this);

        setOnClickListenerButton = findViewById(R.id.button_setOnclickListener_showTime);
        setOnClickListenerButton.setOnClickListener(new MyOnClickListener(timeTextVie));
    }

    static class MyOnClickListener implements View.OnClickListener {
        private TextView timeTextVie;
        public MyOnClickListener(TextView textView) {
            this.timeTextVie = textView;
        }
        @Override
        public void onClick(View view) {
            String nowString = String.format("%s 你点击了 %s", DateUtils.getNowTime(), ((Button) view).getText());
            timeTextVie.setText(nowString);
        }
    }


    public void showTime(View view)  {
        String nowString = String.format("%s 你点击了 %s", DateUtils.getNowTime(), ((Button) view).getText());
        timeTextVie.setText(nowString);
    }

    public void backPage(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_publish_showTime) {
            String nowString = String.format("%s 你点击了 %s", DateUtils.getNowTime(), ((Button) view).getText());
            timeTextVie.setText(nowString);
        }
    }
}

