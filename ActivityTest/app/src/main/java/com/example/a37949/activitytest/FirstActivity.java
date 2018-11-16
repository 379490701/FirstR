package com.example.a37949.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_clear, button_plus, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_minus, button_divide, button_dot, button_multiply, button_equal;
    TextView text;
    boolean clear_flag;

    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        text = findViewById(R.id.text_show);
        button_clear = findViewById(R.id.button_clear);
        button_plus = findViewById(R.id.button_plus);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);
        button_0 = findViewById(R.id.button_0);
        button_minus = findViewById(R.id.button_minus);
        button_multiply = findViewById(R.id.button_multiply);
        button_dot = findViewById(R.id.button_dot);
        button_divide = findViewById(R.id.button_divide);
        button_equal = findViewById(R.id.button_equal);

        button_clear.setOnClickListener(this);
        button_plus.setOnClickListener(this);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        button_5.setOnClickListener(this);
        button_6.setOnClickListener(this);
        button_7.setOnClickListener(this);
        button_8.setOnClickListener(this);
        button_9.setOnClickListener(this);
        button_0.setOnClickListener(this);
        button_minus.setOnClickListener(this);
        button_multiply.setOnClickListener(this);
        button_divide.setOnClickListener(this);
        button_equal.setOnClickListener(this);
        button_dot.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {     //每点击一次按钮
        String string = text.getText().toString();     //获得已输入的内容，存在string中
        switch (v.getId()) {     //v表示一个点击动作发生
            case R.id.button_0:
            case R.id.button_1:
            case R.id.button_2:
            case R.id.button_3:
            case R.id.button_4:
            case R.id.button_5:
            case R.id.button_6:
            case R.id.button_7:
            case R.id.button_8:
            case R.id.button_9:
            case R.id.button_dot:
                if (clear_flag) {     //如果清空标志=true（输出结果之后，清空标志置成true）
                    clear_flag = false;     //清空标志置成false
                    string = "";     //string置成空
                    text.setText("");     //输出框置成空
                    Log.d(TAG, "点击了数字按钮，但清空标志为true，string和输出框置为空");
                }
                text.setText(string + ((Button) v).getText());     //把string中的内容和点击动作获得的内容显示在输出框中
                Log.d(TAG, "点击了数字按钮，输出string和点击的数字");
                break;
            case R.id.button_plus:
            case R.id.button_minus:
            case R.id.button_multiply:
            case R.id.button_divide:
                if (clear_flag) {     //同样判断清空标志
                    clear_flag = false;
                    text.setText("");
                    Log.d(TAG, "点击了符号按钮，但清空标志为true，输出框置为空");
                }
                if (!string.contains(" ")) {
                    text.setText(string + " " + ((Button) v).getText() + " ");     //每个运算符前后加一个空格
                    Log.d(TAG, "点击了符号按钮，且string中不含符号，输出string和点击的符号");
                } else {
                    text.setText(string);
                    Log.d(TAG, "点击了符号按钮，但string中含符号，输出string");
                }
                break;
            case R.id.button_clear:
                clear_flag = false;
                string = "";
                text.setText("");
                Log.d(TAG, "点击了清空按钮，清空string");
                break;
            case R.id.button_equal:
                if (!string.substring(string.indexOf(" ") + 3).equals("")) {     //数字2不为空
                    Log.d(TAG, "点击了等号按钮，输出结果");
                    getResult();
                } else {
                    text.setText(string);
                }
                break;
        }
    }

    private void getResult() {
        String str = text.getText().toString();     //获得已输入的内容，存在str中

        if (!str.contains(" ")) {     //不包含运算符，不进行运算（含运算符必含空格）
            Log.d(TAG, "不包含运算符，不进行运算");
            return;
        }

        clear_flag = true;     //运算完成一次自动清空

        if (!str.contains(")")) {

            String str1 = str.substring(0, str.indexOf(" "));     //截取运算符之前的字符，位置为从0开始到第一个空格出现的地方
            String str2 = str.substring(str.indexOf(" ") + 1, str.indexOf(" ") + 2);     //截取运算符，位置为从第一个空格到第二个空格之间
            String str3 = str.substring(str.indexOf(" ") + 3);     //截取运算符后面的数字

            double result = 0;

            if (!str1.equals("") && !str3.equals("")) {     //如果运算数1和运算数2均不为空

                //将字符类型转化为数值类型
                double num1 = Double.parseDouble(str1);
                double num2 = Double.parseDouble(str3);

                //加减乘除运算，得到运算结果result
                if (str2.equals("+")) {
                    result = num1 + num2;
                } else if (str2.equals("-")) {
                    result = num1 - num2;
                } else if (str2.equals("*")) {
                    result = num1 * num2;
                } else if (str2.equals("/")) {
                    if (num2 == 0) {
                        result = 0;
                    } else {
                        result = num1 / num2;
                    }
                }

                //小数点位数精确
                if (!str1.contains(".") && !str3.contains(".") && !str2.equals("/")) {     //如果运算数1和运算数2均为整数，且非除法
                    int r = (int) result;
                    text.setText(r + "");
                } else {
                    text.setText(result + "");
                }

            } else if (str1.equals("") && !str3.equals("")) {     //如果运算数1为空，且运算数2不为空

                double num2 = Double.parseDouble(str3);

                if (str2.equals("+")) {
                    result = 0 + num2;
                } else if (str2.equals("-")) {
                    result = 0 - num2;
                } else if (str2.equals("/") || str2.equals("*")) {
                    result = 0;
                }
                Log.d(TAG, "运算数1为空，且运算数2不为空");

                //小数点位数精确
                if (!str3.contains(".")) {
                    int r = (int) result;
                    text.setText(r + "");
                } else {
                    text.setText(result + "");
                }

            }

        }

    }

}