package com.example.chapter06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareWriteActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_name;
    EditText et_age;
    EditText et_height;
    EditText et_weight;

    CheckBox ck_married;

    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share_write);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);
        findViewById(R.id.btn_save).setOnClickListener(this);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        readInfo();
    }

    private void readInfo() {
        String name = preferences.getString("name", null);
        if (name != null) {
            et_name.setText(name);
        }

        int age = preferences.getInt("age", 0);
        if (age != 0) {
            et_age.setText(String.valueOf(age));
        }

        float height = preferences.getFloat("height", 0.0F);
        if (height != 0.0) {
            et_height.setText(String.valueOf(height));
        }

        float weight = preferences.getFloat("weight", 0.0F);
        if (weight != 0.0) {
            et_weight.setText(String.valueOf(weight));
        }

        boolean married = preferences.getBoolean("married", false);
        ck_married.setChecked(married);

    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putInt("age", Integer.parseInt(age));
        editor.putFloat("height", Float.parseFloat(height));
        editor.putFloat("weight", Float.parseFloat(weight));
        editor.putBoolean("married", ck_married.isChecked());
        editor.commit();

    }
}