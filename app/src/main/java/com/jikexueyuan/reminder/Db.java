package com.jikexueyuan.reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by huyiqing on 2016/11/23.
 */

public class Db extends SQLiteOpenHelper {
    public Db(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE list(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "thing TEXT DEFAULT \"\"," +
                "time INTEGER DEFAULT \"\")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
