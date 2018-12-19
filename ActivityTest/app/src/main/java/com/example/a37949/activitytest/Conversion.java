package com.example.a37949.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Conversion extends AppCompatActivity implements View.OnClickListener {

    Button button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0;
    Button button_clear, button_equal;
    TextView text;
    boolean clear_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

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
        button_equal = findViewById(R.id.button_equal);
        button_clear = findViewById(R.id.button_clear);
        text = findViewById(R.id.text);

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
        button_clear.setOnClickListener(this);
        button_equal.setOnClickListener(this);

        text.setText("英寸：");
    }

    @Override
    public void onClick(View v) {
        String string = text.getText().toString();//获得已输入的内容，存在string中
        switch (v.getId()) {//v表示一个点击动作发生
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
                if (clear_flag) {//如果清空标志=true（输出结果之后，清空标志置成true）
                    clear_flag = false;//清空标志置成false
                    string = "";//string置成空
                    text.setText("");//输出框置成空
                }
                text.setText(string + ((Button) v).getText());//把string中的内容和点击动作获得的内容显示在输出框中
                break;
            case R.id.button_clear:
                clear_flag = false;
                text.setText("");
                break;
            case R.id.button_equal:
                clear_flag = true;
                String str = text.getText().toString();
                str = str.replace("英寸：", "");
                int temp = Integer.parseInt(str);
                double result = 2.54 * temp;
                text.setText("厘米：" + result);
                break;
        }
    }
}
