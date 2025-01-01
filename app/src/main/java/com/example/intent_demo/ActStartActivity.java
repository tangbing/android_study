package com.example.intent_demo;

import android.content.Intent;
import android.nfc.Tag;
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

public class ActStartActivity extends AppCompatActivity implements View.OnClickListener {

    private static String Tag = "finish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_act_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(Tag,"ActStartActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(Tag,"ActStartActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Tag,"ActStartActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(Tag,"ActStartActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Tag,"ActStartActivity onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Tag,"ActStartActivity onDestroy");
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ActFinishActivity.class));
    }
}