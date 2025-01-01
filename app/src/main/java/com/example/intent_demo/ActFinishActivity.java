package com.example.intent_demo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActFinishActivity extends AppCompatActivity implements View.OnClickListener {
    private static String Tag = "finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_finish);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_finish).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(Tag,"ActFinishActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"ActFinishActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"ActFinishActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"ActFinishActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"ActFinishActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"ActFinishActivity onDestroy");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() ==  R.id.btn_finish || v.getId() == R.id.iv_back) {
            finish();
        }
    }
}