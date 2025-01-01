package com.example.chapter05;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    CheckBox ck_rember;
    Button bt_forget;

    TextView tv_loginByCode;
    EditText et_pwd;
    EditText et_phone;

    Button btn_login;

    RadioButton radio_code;
    RadioButton radio_pwd;

    private String mPassword = "123456";

    private String mVerifyCode;

    private ActivityResultLauncher<Intent> register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        RadioGroup rb_login = findViewById(R.id.rg_login);
        ck_rember = findViewById(R.id.ck_rember);
        bt_forget = findViewById(R.id.btn_forget);
        tv_loginByCode = findViewById(R.id.tv_loginByCode);
        et_pwd = findViewById(R.id.et_pwd);
        et_phone = findViewById(R.id.et_phone);
        btn_login = findViewById(R.id.btn_login);

        radio_code = findViewById(R.id.radio_code);
        radio_pwd = findViewById(R.id.radio_pwd);


        // 设置单选监听器
        rb_login.setOnCheckedChangeListener(this);

        et_phone.addTextChangedListener(new HideTextWatcher(et_phone, 11));
        et_pwd.addTextChangedListener(new HideTextWatcher(et_phone, 6));

        bt_forget.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Intent intent = result.getData();
            if (intent != null && result.getResultCode() == LoginMainActivity.RESULT_OK) {
                mPassword = intent.getStringExtra("new_password");
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_code:
                tv_loginByCode.setText(getString(R.string.verifycode));
                et_pwd.setHint(getString(R.string.input_verifycode));
                bt_forget.setText(getString(R.string.verifycode));
                ck_rember.setVisibility(View.GONE);
                break;

            case R.id.radio_pwd:
            bt_forget.setText(getString(R.string.forget_password));
            et_pwd.setHint(getString(R.string.input_password));
            tv_loginByCode.setText(getString(R.string.login_password));
            ck_rember.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String phone = et_phone.getText().toString();
        if (phone.length() < 11) {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (v.getId()) {
            case R.id.btn_forget:
                if (radio_pwd.isChecked()) {
                    Intent intent = new Intent(this, LoginForgetActivity.class);
                    intent.putExtra("phone", phone);
                    register.launch(intent);

                } else if (radio_code.isChecked()) {
                    mVerifyCode = String.format("%06d", new Random().nextInt(999999));
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + phone + ", 本次验证码是" + mVerifyCode + ",请输入验证码");
                    builder.setPositiveButton("ok", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;

            case R.id.btn_login:
                // 验证码方式校验
                if (radio_code.isChecked()) {
                    if (!mVerifyCode.equals(et_pwd.getText().toString())) {
                        Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess();
                } else if (radio_pwd.isChecked()) {                // 密码方式校验
                    if (!mPassword.equals(et_pwd.getText().toString())) {
                        Toast.makeText(this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loginSuccess();
                }
                break;
        }
    }

    private  void loginSuccess() {
        String desc = String.format("您的手机号码是%s, 恭喜你通过登录验证，点击“确定”按钮返回上个页面",et_phone.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登陆成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", (dialog, which) -> {
            // 结束当前的活动界面
           finish();
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 定义一个编辑框监听器，在输入文本达到指定长度时自动隐藏输入法
    private class HideTextWatcher implements TextWatcher {

        EditText mView;
        int mMaxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.mView = et;
            this.mMaxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.toString().length() == mMaxLength) {
                ViewUtil.hideOneInputMethod(LoginMainActivity.this, mView);
            }
        }
    }
}