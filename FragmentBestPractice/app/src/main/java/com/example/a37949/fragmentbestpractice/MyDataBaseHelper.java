package com.example.a37949.fragmentbestpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

//新建一个类
public class MyDataBaseHelper extends SQLiteOpenHelper {

    //建表语句定义成字符常量
    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    public static final String CREATE_WORD_LIST = "create table WordList ("
            + "id integer primary key autoincrement, "
            + "word_content text, "
            + "word_meaning text)";

    public static final String CREATE_NEW_WORD = "create table NewWord ("
            + "id integer primary key autoincrement, "
            + "word_content text, "
            + "word_meaning text)";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //执行SQL语句
        db.execSQL(CREATE_WORD_LIST);//一个单词表
        db.execSQL(CREATE_NEW_WORD);//一个生词本
        db.execSQL(CREATE_BOOK);//Book用作测试表
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists WordList");
        db.execSQL("drop table if exists NewWord");
        onCreate(db);
    }
}
