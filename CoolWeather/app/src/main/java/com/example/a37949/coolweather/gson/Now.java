package com.example.a37949.coolweather.gson;

//"now":{
//"tmp":"29",
//"cond":{"txt":"阵雨"}
//}

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}