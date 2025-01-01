package com.example.chapter05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class LoginForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_password_first;
    private EditText et_password_second;
    private EditText et_verifycode;

    private  String mPhone;

    private String mVerifyCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_forget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_password_first = findViewById(R.id.et_password_first);
        et_password_second = findViewById(R.id.et_password_second);
        et_verifycode = findViewById(R.id.et_verifycode);

        Bundle bundle = getIntent().getExtras();
        String phone = bundle.getString("phone");
        mPhone = phone;

        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.btn_verifyCode).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verifyCode:
                mVerifyCode = String.format("%06d", new Random().nextInt(999999)).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请记住密码");
                builder.setMessage("手机号：" + mPhone + "本次验证码是" + mVerifyCode + ",请输入验证码");
                builder.setPositiveButton("ok", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.btn_confirm:
                String password_first = et_password_first.getText().toString();
                String password_second = et_password_second.getText().toString();
                if (password_first.length() < 6) {
                    Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password_first.equals(password_second)) {
                    Toast.makeText(this, "两次输入的新密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mVerifyCode.equals(et_verifycode.getText().toString())) {
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(this,"密码修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("new_password", password_first);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}