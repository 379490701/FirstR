package com.example.a37949.coolweather.db;

import org.litepal.crud.DataSupport;

//LitePal中的每一个实体类都必须继承自DataSupport类
public class Province extends DataSupport {

    private int id;//每个实体类中都应该有的字段
    private String provinceName;//记录省的名字
    private int provinceCode;//记录省的代码

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }
}
