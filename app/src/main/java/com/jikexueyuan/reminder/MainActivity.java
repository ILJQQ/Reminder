package com.jikexueyuan.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter simpleAdapter;
    private ListView lvReminder;
    private Db db;
    private SQLiteDatabase dbReader,dbWriter;
    public static final int NOTIFICATION_ID = 1300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvReminder = (ListView) findViewById(R.id.lvReminder);

        db = new Db(this);
        dbReader = db.getReadableDatabase();
        dbWriter = db.getWritableDatabase();

        simpleAdapter = new SimpleCursorAdapter(this,R.layout.reminder_list,null,
                new String[]{"time","thing"}, new int[]{R.id.list_text,R.id.sub_text});
        lvReminder.setAdapter(simpleAdapter);
        refreshReminder();

        startService(new Intent(MainActivity.this,MyService.class));

        lvReminder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提醒")
                        .setMessage("你确定要删除该提醒事项吗？")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cursor cursor = simpleAdapter.getCursor();
                                cursor.moveToPosition(position);

                                int itemId = cursor.getInt(cursor.getColumnIndex("_id"));
                                dbWriter.delete("list","_id=?",new String[]{itemId + ""});
                                refreshReminder();
                                Toast.makeText(MainActivity.this,"删除成功!",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void refreshReminder(){
        Cursor c = dbReader.query("list",null,null,null,null,null,null);
        simpleAdapter.changeCursor(c);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshReminder");
        registerReceiver(brReceiver,intentFilter);
    }

    private BroadcastReceiver brReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshReminder")){
                refreshReminder();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(brReceiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(MainActivity.this, addToReminder.class);
        startActivity(i);
        return true;
    }
}
