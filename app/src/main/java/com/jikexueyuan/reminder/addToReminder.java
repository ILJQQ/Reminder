package com.jikexueyuan.reminder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addToReminder extends AppCompatActivity {
    private Button btnSave;
    private EditText etTime,etThing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_reminder);

        etTime = (EditText) findViewById(R.id.etTime);
        etThing = (EditText) findViewById(R.id.etThing);

        final Db db = new Db(this);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTime.length() == 0 || etThing.length() == 0){
                    Toast.makeText(addToReminder.this,"输入不完整请仔细输入",Toast.LENGTH_SHORT).show();
                }else {
                    SQLiteDatabase dbWrite = db.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    int aTime = Integer.parseInt(etTime.getText().toString());
                    String aThing = etThing.getText().toString();
                    if (aTime < 0 || aTime > 23){
                        Toast.makeText(addToReminder.this,"请输入0~24小时之间的整数数字", Toast.LENGTH_SHORT).show();
                        etTime.setText("");
                    }else {
                        int fTime = aTime;
                        cv.put("time", fTime);
                        cv.put("thing", aThing);
                        dbWrite.insert("list", null, cv);
                        Intent i = new Intent();
                        i.setAction("action.refreshReminder");
                        sendBroadcast(i);
                        dbWrite.close();
                        Toast.makeText(addToReminder.this, "保存成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
