package com.example.a37949.coolweather.gson;

//这个数据项比较特殊，其中包含的是一个数组，数组中的每一项代表未来一天的天气信息
//针对这种情况，我们只需要定义出单日天气实体类就行了
//然后在声明实体类引用的时候，使用集合类型来进行声明
//"daily_forecast":[
//{
//"date":"2016-08-08",
//"cond":{"txt_d":"阵雨"},
//"tmp":{"max":"34", "min":"27"}
//},
//{
//"date":"2016-08-09",
//"cond":{"txt_d":"多云"},
//"tmp":{"max":"35", "min":"29"}
//},
//...
//]

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}
