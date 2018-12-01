package com.example.a37949.fragmentbestpractice;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.a37949.fragmentbestpractice.MainActivity.db;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        TextView show_research = (TextView) findViewById(R.id.show_research);
        Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();

        //查询Book表中所有的数据
        Cursor cursor = db.query("Book", null, null, null, null, null, null);
        int i = 0;
        if (cursor.moveToFirst()) {
            do {
                //遍历Cursor对象，取出数据并打印
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                if (name.equals(text)) {
                    show_research.setText(author);
                    i = 1;//找到了
                    break;
                }
            } while (cursor.moveToNext());
            if (i == 0) {
                show_research.setText("未找到该单词！7");
            }
        }
        cursor.close();
    }
}
