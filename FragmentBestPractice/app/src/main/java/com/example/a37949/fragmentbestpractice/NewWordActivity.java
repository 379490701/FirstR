package com.example.a37949.fragmentbestpractice;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.a37949.fragmentbestpractice.MainActivity.db;
import static com.example.a37949.fragmentbestpractice.MainActivity.mString;

public class NewWordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        TextView newWord = findViewById(R.id.new_word);
        //查询NewWord表中所有的数据
        Cursor cursor = db.query("NewWord", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //遍历Cursor对象，取出数据并打印
                String word_content = cursor.getString(cursor.getColumnIndex("word_content"));
                String word_meaning = cursor.getString(cursor.getColumnIndex("word_meaning"));
                mString = mString + word_content + " " + word_meaning + "\n";
            } while (cursor.moveToNext());
        }
        cursor.close();
        newWord.setText(mString);
    }
}
