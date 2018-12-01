package com.example.a37949.fragmentbestpractice;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String mString = "";
    public static MyDataBaseHelper dbHelper;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //启动活动即创建数据库
        dbHelper = new MyDataBaseHelper(this, "WORD.db", null, 1);
        db = dbHelper.getWritableDatabase();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button_search = (Button) findViewById(R.id.button_search);
        final EditText searchView = (EditText) findViewById(R.id.search_view);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchView.getText().toString();
//                Log.d("123456", text);
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("text", text);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
