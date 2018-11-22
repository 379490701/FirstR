package com.example.a37949.test;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a37949.test.Words.WordsContent;

public class Test extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, DetailFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
//        Button button1 = (Button) findViewById(R.id.button1);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //隐式启动一个新活动
//                Intent intent = new Intent("com.example.activitytest.ACTION_START");
//                intent.addCategory("com.example.activitytest.MY_CATEGORY");
//
//                //启动一个浏览器新活动
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://www.baidu.com"));
//
//                //启动拨号活动，失败？？？
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("10086"));
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onFragmentInteraction(String id, Fragment fragment) {
        Bundle arguments = new Bundle();
        arguments.putString("id", id);
//        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(arguments);

        //2018年11月19日16:28:54编辑到这里，出现未知错误
        getFragmentManager().beginTransaction().replace(R.id.worddetail, fragment).commit();

    }

    @Override
    public void onListFragmentInteraction(WordsContent.DummyItem item) {

    }
}
