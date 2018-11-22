package com.example.a37949.activitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Stack;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    Button button_clear, button_plus, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9, button_0, button_minus, button_divide, button_dot, button_multiply, button_equal, button_left, button_right, button_delete;
    TextView text;
    boolean clear_flag;

    private static final String TAG = "FirstActivity";

    //注册事件
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
        button_left = findViewById(R.id.button_left);
        button_right = findViewById(R.id.button_right);
        button_delete = findViewById(R.id.button_delete);

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
        button_left.setOnClickListener(this);
        button_right.setOnClickListener(this);
        button_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {//每点击一次按钮
        String string = text.getText().toString();//获得已输入的内容，存在string中
        int i;
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
            case R.id.button_dot:
            case R.id.button_left:
            case R.id.button_right:
                if (clear_flag) {//如果清空标志=true（输出结果之后，清空标志置成true）
                    clear_flag = false;//清空标志置成false
                    string = "";//string置成空
                    text.setText("");//输出框置成空
                    Log.d(TAG, "点击了数字按钮，但清空标志为true，string和输出框置为空");
                }
                text.setText(string + ((Button) v).getText());//把string中的内容和点击动作获得的内容显示在输出框中
                Log.d(TAG, "点击了数字按钮，输出string和点击的数字");
                break;
            case R.id.button_plus:
            case R.id.button_minus:
            case R.id.button_multiply:
            case R.id.button_divide:
                if (clear_flag) {//同样判断清空标志
                    clear_flag = false;
                    text.setText("");
                    Log.d(TAG, "点击了符号按钮，但清空标志为true，输出框置为空");
                }
                if (string.equals("")) {
                    text.setText(string);
                    break;
                }
                i = string.length() - 1;
                if (string.charAt(i) == ' ') {//前一个字符也为运算符
                    string = string.substring(0, i - 2);
                    text.setText(string + " " + ((Button) v).getText() + " ");
                    Log.d(TAG, "点击了符号按钮，但是当前字符为运算符，输出修改后的运算符");
                } else {
                    text.setText(string + " " + ((Button) v).getText() + " ");//运算符前后加一个空格
                    Log.d(TAG, "点击了符号按钮，输出string和点击的符号");
                }
                break;
            case R.id.button_delete:
                if (clear_flag) {//同样判断清空标志
                    clear_flag = false;
                    text.setText(string);
                    Log.d(TAG, "点击了删除按钮，但清空标志为true，输出框置为运算结果");
                } else {
                    if (string.equals("")) {
                        text.setText("");
                        break;
                    }
                    i = string.length() - 1;
                    if (string.charAt(i) == ' ') {
                        i = i - 2;
                    }
                    string = string.substring(0, i);
                    text.setText(string);
                    Log.d(TAG, "点击了删除按钮，清空标志为false，输出string-1");
                }
                break;
            case R.id.button_clear:
                clear_flag = false;
                text.setText("");
                Log.d(TAG, "点击了清空按钮，清空string");
                break;
            case R.id.button_equal:
                Log.d(TAG, "点击了等号按钮，输出结果");
                if (!string.contains(" ")) {//不包含运算符，不进行运算（含运算符必含空格）
                    text.setText(string);
                } else {
                    String str = text.getText().toString();
                    double res = Result(str);
                    text.setText(res + "");
                }
                break;
        }
    }

    //判断运算符优先级
    protected int Priority(char s) {
        switch (s) {
            case '(':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    //使用堆栈实现运算
    protected double Result(String str) {
        clear_flag = true;//运算完成一次自动清空
        int i = 0, flag = 0, error = 0;
        double j, tmp = 0, buf = 1, sum, res = 0;
        Stack num = new Stack();
        Stack opt = new Stack();
        while ((!opt.empty() || i < str.length()) && error == 0) {//读取字符串，当字符串不为空或运算符栈不为空时
            char s;
            if (i < str.length()) {
                s = str.charAt(i);
            } else {
                s = '\0';
            }
            if (s == ' ') {
                i++;
                continue;
            }
            if ((s >= '0' && s <= '9') || s == '.') {//属于数值部分
                if (s != '.' && flag == 0) {//是整数
                    tmp = tmp * 10 + s - '0';//如果是整数数字，整数进位+1
                } else if (s == '.' || (s >= '0' && s <= '9')) {//如果是小数，小数进位+1
                    flag = 1;//是小数
                    if (s >= '0' && s <= '9') {
                        buf = buf / 10;
                        res = res + (s - '0') * buf;
                    }
                }
                sum = tmp + res;
                i++;
                char m;
                if (i < str.length()) {
                    m = str.charAt(i);
                } else {
                    m = '\0';
                }
                if ((m < '0' || m > '9') && m != '.') {//如果数字后面的字符不是数字，则一个操作数结束
                    num.push(sum);//操作数放入堆栈
                    tmp = 0;//进位清空
                    flag = 0;
                    buf = 1;
                    res = 0;
                }
            } else {//不是数字
                //如果（运算符堆栈为空）或（运算符栈顶为左括号，且下一个字符不是右括号）或（字符的优先级要大于栈顶元素的优先级）
                if (opt.empty() || ((char) opt.peek() == '(' && s != ')') || Priority(s) > Priority((char) opt.peek())) {
                    opt.push(s);//运算符入栈
                    i++;
                    continue;
                }
                //如果运算符堆栈栈顶为左括号并且当前运算符为右括号
                if ((char) opt.peek() == '(' && s == ')') {
                    opt.pop();//运算符出栈，即左括号出栈
                    i++;
                    continue;
                }
                //如果（字符为空，且操作符栈不为空）或（字符为右括号）且（运算符栈顶不为左括号）或（字符的优先级要小于栈顶元素的优先级）
                if ((s == '\0' && !opt.empty()) || s == ')' && (char) opt.peek() != '(' || Priority(s) <= Priority((char) opt.peek())) {
                    switch ((char) opt.pop()) {
                        case '+':
                            num.push((double) num.pop() + (double) num.pop());
                            break;
                        case '-':
                            j = (double) num.pop();
                            num.push((double) num.pop() - j);
                            break;
                        case '*':
                            num.push((double) num.pop() * (double) num.pop());
                            break;
                        case '/':
                            j = (double) num.pop();
                            if (j == 0) {
                                error = 1;
                                break;
                            }
                            num.push((double) num.pop() / j);
                            break;
                    }
                    continue;
                }//if如果（字符为空，且操作符栈不为空）或（字符为右括号）且（运算符栈顶不为左括号）或（字符的优先级要小于栈顶元素的优先级）
            }//else不是数字
        }//while
        if (error == 1) {
            return 0;
        }
        return (double) num.pop();
    }//Result()

    //简单的运算方法
    private void getResult() {
        String str = text.getText().toString();//获得已输入的内容，存在str中
        if (!str.contains(" ")) {//不包含运算符，不进行运算（含运算符必含空格）
            Log.d(TAG, "不包含运算符，不进行运算");
            return;
        }
        clear_flag = true;//运算完成一次自动清空
        if (!str.contains(")")) {
            String str1 = str.substring(0, str.indexOf(" "));//截取运算符之前的字符，位置为从0开始到第一个空格出现的地方
            String str2 = str.substring(str.indexOf(" ") + 1, str.indexOf(" ") + 2);//截取运算符，位置为从第一个空格到第二个空格之间
            String str3 = str.substring(str.indexOf(" ") + 3);//截取运算符后面的数字
            double result = 0;
            if (!str1.equals("") && !str3.equals("")) {//如果运算数1和运算数2均不为空
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
                if (!str1.contains(".") && !str3.contains(".") && !str2.equals("/")) {//如果运算数1和运算数2均为整数，且非除法
                    int r = (int) result;
                    text.setText(r + "");
                } else {
                    text.setText(result + "");
                }
            } else if (str1.equals("") && !str3.equals("")) {//如果运算数1为空，且运算数2不为空
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
    }//getReslut()
}
