package com.example.a37949.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {

    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
        //由于Okhttp的出色封装，这里和服务器进行交互的代码仅仅3行
        //现在发起一条HTTP请求只需要调用sendOkHttpRequest()方法，传入请求地址，并注册一个回调来处理服务器响应就可以
        //另外服务器返回的省市县数据都是JSON格式的，需要一个工具类（Utility类）来解析和处理这种数据
    }
}
