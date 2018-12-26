package com.example.a37949.fragmentbestpractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.a37949.fragmentbestpractice.MainActivity.db;

//通过点击列表获取某个项目信息的activity
public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, String newsTitle, String newsContent) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title", newsTitle);
        intent.putExtra("news_content", newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        String newsTitle = getIntent().getStringExtra("news_title");//获取传入的新闻标题
        String newsContent = getIntent().getStringExtra("news_content");//获取传入的新闻内容
        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle, newsContent);//刷新NewsContent界面

        Button add = (Button) findViewById(R.id.add);
        Button look = (Button) findViewById(R.id.look);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word_content = getIntent().getStringExtra("news_title");
                String word_meaning = getIntent().getStringExtra("news_content");
                //开始组装数据
                ContentValues values = new ContentValues();
                values.put("word_content", word_content);
                values.put("word_meaning", word_meaning);
                db.insert("NewWord", null, values);
                values.clear();

            }
        });

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsContentActivity.this, NewWordActivity.class);
                startActivity(intent);
            }
        });
    }
}
