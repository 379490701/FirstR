package com.example.a37949.coolweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//创建一个总的实例类来引用刚刚创建的各个实体类
//在weather类中，我们对Basic、AQI、Now、Suggestion、Forecast类进行了引用
//其中由于daily_forecast中包含的是一个数组，因此使用了List集合来引用Forecast类
//另外，返回的天气数据还包含一项status数据，成功返回ok，失败则返回具体原因，那么这里也需要添加一个对应的status字段
public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
