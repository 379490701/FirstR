package com.example.a37949.coolweather.util;

import android.text.TextUtils;

import com.example.a37949.coolweather.db.City;
import com.example.a37949.coolweather.db.County;
import com.example.a37949.coolweather.db.Province;
import com.example.a37949.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//handleProvinceResponse()、handleCityResponse()、handleCountyResponse()这3个方法都用于解析和处理服务器返回的数据
//处理方式类似：先使用JSONArray和JSONObject将数据解析出来，然后组装成实体类对象，再调用save()方法将数据存储到数据库中
//由于这里的JSON数据结构比较简单，就不使用GSON来进行解析了
public class Utility {

    //解析和处理服务器返回的省级数据
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的市级数据
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //解析和处理服务器返回的县级数据
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //将返回的JSON数据解析成weather实体类
    public static Weather handleWeatherResponse(String response) {
        try {
            //先通过JSONObject和JSONArray将天气数据中的主体内容解析出来
            //即如下内容
            //{
            //"status":"ok",
            //"basic":{},
            //"aqi":{},
            //"now":{},
            //"suggestion":{},
            //"daily_forecast":[]
            //}
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            //由于之前已经按照上面的数据格式定义过相应的GSON实体类
            //因此只需要通过调用fromJson()方法就能直接将JSON数据转换成Weather对象
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}//Utility类
