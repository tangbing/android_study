package com.example.chapter05;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrawableShapeActivity extends AppCompatActivity implements View.OnClickListener {

    View v_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drawable_shape);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_rect).setOnClickListener(this);
        findViewById(R.id.btn_oval).setOnClickListener(this);

        v_content = findViewById(R.id.v_content);
        // v_content背景设置为圆角矩形
        v_content.setBackgroundResource(R.drawable.shape_rect_gold);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rect:
                v_content.setBackgroundResource(R.drawable.shape_rect_gold);
                break;
            case R.id.btn_oval:
                v_content.setBackgroundResource(R.drawable.shape_oval_rose);

                break;
        }
    }
}