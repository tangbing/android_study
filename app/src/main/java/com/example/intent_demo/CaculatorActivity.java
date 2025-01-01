package com.example.intent_demo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CaculatorActivity extends AppCompatActivity implements View.OnClickListener  {

    private TextView tv_result;
    // 第一个操作数
    private String firstNum  = "";
    // 运算符
    private String operator  = "";
    // 第二个操作数
    private String secondNum  = "";
    // 当前的计算结果
    private String result  = "";
    // 显示的文本内容
    private String showText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_caculator);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        tv_result = findViewById(R.id.tv_result);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_multiply).setOnClickListener(this);
        findViewById(R.id.btn_clear).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.ib_sqrt).setOnClickListener(this);
        findViewById(R.id.btn_reciprocal).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_dot).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String inputText = "";

        if (v.getId() == R.id.ib_sqrt) {
            inputText = "√";
        } else {
            inputText = ((TextView) v).getText().toString();
        }
        switch (v.getId()) {
            case R.id.btn_clear:
                clear();
                break;
            case R.id.btn_cancel:
                break;
            // 点击了加、减、乘、除按钮
            case R.id.btn_plus:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                operator = inputText;
                refreshText(showText + inputText);
                break;
            case R.id.btn_equal:
                double calculate_result = handlerOperator();
                refreshOperate(String.valueOf(calculate_result));
                inputText = "=" + String.valueOf(calculate_result);
                refreshText(showText + inputText);
                break;
            case R.id.btn_reciprocal:
                double reciprocal_result = 1.0 / Double.parseDouble(firstNum);
                refreshOperate(String.valueOf(reciprocal_result));
                refreshText(showText + "/=" + result);
                break;
            case R.id.ib_sqrt:
                double sqrt_result = Math.sqrt(Double.parseDouble(firstNum));
                refreshOperate(String.valueOf(sqrt_result));
                refreshText(showText + "√=" + result);
                break;
            // 点击了其他按钮，包括数字和小数点
            default:
                if (result.length() >0 && operator.equals("")) {
                    clear();
                }
                // 无运算符，则继续拼接第一个操作数
                if (operator.equals("")) {
                    firstNum =  firstNum + inputText;
                } else {// 有运算符，则继续拼接第二个操作数
                    secondNum  = secondNum + inputText;
                }

                if (showText.equals("0") && !inputText.equals(".")) {
                    refreshText(inputText);
                } else {
                    refreshText(showText + inputText);
                }
                break;
        }
    }

    private Double handlerOperator() {
        Log.d("handlerOperator", "operator: "  +  operator + " first:"  + firstNum + " second:" +  secondNum);
        if (operator.equals("＋")) {
            return Double.parseDouble(firstNum) + Double.parseDouble(secondNum);
        }  else if (operator.equals("－")) {
            return Double.parseDouble(firstNum) - Double.parseDouble(secondNum);
        } else if (operator.equals("×")) {
            return Double.parseDouble(firstNum) * Double.parseDouble(secondNum);
        } else {
            return Double.parseDouble(firstNum) / Double.parseDouble(secondNum);
        }
    }

    private void refreshOperate(String text) {
        result =  text;
        firstNum = result;
        secondNum = "";
        operator =  "";
    }

    // 刷新文本显示
    private void refreshText(String text) {
        showText = text;
        tv_result.setText(showText);
    }
    private void clear() {
        refreshOperate("");
        refreshText("");
    }
}