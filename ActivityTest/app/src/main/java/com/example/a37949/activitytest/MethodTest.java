package com.example.a37949.activitytest;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@SuppressWarnings("ALL")
public class MethodTest {

    public static long getNFactorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * getNFactorial(n - 1);
    }

    public static String binaryToDecimal(int n) {
        String str = "";
        while (n != 0) {
            str = n % 2 + str;
            n = n / 2;
        }
        return str;
    }

    public static void main(String args[]) {
        BigDecimal b = new BigDecimal(Math.cos(Math.toRadians(90)));
        String string = " sin 3";
        string = string.replace("sin", "s");
        System.out.print(binaryToDecimal(8));
    }

}
