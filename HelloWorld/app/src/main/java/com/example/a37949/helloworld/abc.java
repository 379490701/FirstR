package com.example.a37949.helloworld;

import java.util.Stack;

public class abc {

    boolean clear_flag;

    //判断运算符优先级
    protected int Priority(char s) {
        switch (s) {
            case '(':
                return 4;
            case '√':
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
                        case '√':
                            num.push(Math.sqrt((double) num.pop()));
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

    public static void main(String[] args) {
        char s = 1;
        abc a = new abc();
        double b = a.Result("√9");
        System.out.print(b);
    }
}
