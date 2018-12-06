package com.example.a37949.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a37949.coolweather.db.City;
import com.example.a37949.coolweather.db.County;
import com.example.a37949.coolweather.db.Province;
import com.example.a37949.coolweather.util.HttpUtil;
import com.example.a37949.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button backButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> dataList = new ArrayList<>();

    //省列表
    private List<Province> provinceList;
    //市列表
    private List<City> cityList;
    //县列表
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的城市
    private City selectedCity;
    //当前选中的级别
    private int currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);

        //在这个方法中先是获取到了一些控件实例
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);

        //然后初始化了ArrayAdapter，并将它设置为ListView的适配器
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //给ListView和Button设置了点击事件，到这里完成初始化工作
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //当点击了某个省的时候就会进入到ListView的onItemClick()方法中，根据级别调用不同的方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == LEVEL_COUNTY) {
                    //从省市列表跳转到天气界面
                    String weatherId = countyList.get(position).getWeatherId();

                    //使用instanceof关键字可以判断一个对象是否属于某个类的实例
                    //在碎片中调用getActivity()方法，可以轻松判断出该碎片是在MainActivity中还是WeatherActivity中
                    if (getActivity() instanceof MainActivity) {
                        //在MainActivity中处理逻辑不变
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    } else if (getActivity() instanceof WeatherActivity) {
                        //在WeatherActivity中关闭滑动菜单，显示下拉刷新进度条，然后请求新城市的天气信息
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swipeRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvinces();
                }
            }
        });
        //在此方法的最后调用了queryProvinces()方法，也就是从这里开始加载省级数据
        queryProvinces();
    }

    //查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
    private void queryProvinces() {
        //此方法首先会将头布局文件的标题设置成“中国”，将返回按钮隐藏起来
        titleText.setText("中国");
        backButton.setVisibility(View.GONE);
        //调用LitePal的查询接口来从数据库中读取省级数据
        provinceList = DataSupport.findAll(Province.class);
        //如果读到了就直接将数据显示在画面上
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            //如果没有读到就组装出一个请求地址，然后调用queryFromServer()方法来从服务器上查询数据
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    //查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where(" provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    //查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }
    }

    //根据传入的地址和类型从服务器上查询省市县数据
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        //调用HttpUtility的sendOkHttpRequest()方法向服务器发送请求
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            //响应的数据会回调到onResponse()方法中
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                //然后再去调用Utility的handleProvinceResponse()方法来解析和处理服务器返回的数据
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountyResponse(responseText, selectedCity.getId());
                }
                //接下来的一步很关键
                //在解析和处理完数据之后，再次调用了queryProvinces()方法来重新加载省级数据
                //由于queryProvinces()方法牵扯到了UI操作，因此必须在主线程中调用
                //这里借助了runOnUiThread()方法来实现从子线程切换到主线程
                //现在数据库中已经存在了数据，因此调用queryProvinces()就会直接将数据显示在界面上
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //通过runOnUiThread()方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载…");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //关闭进程对话框
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

}//ChooseAreaFragment类