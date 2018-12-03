package com.example.a37949.coolweather.db;

import org.litepal.crud.DataSupport;

public class County extends DataSupport {

    private int id;//每个实体类中都应该有的字段
    private String countyName;//记录县的名字
    private String weatherId;//县对应的id
    private int cityId;//记录当前县所属市的id值

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCountyName(String countryName) {
        this.countyName = countryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public String getCountyName() {
        return countyName;
    }

    public int getId() {
        return id;
    }
}
