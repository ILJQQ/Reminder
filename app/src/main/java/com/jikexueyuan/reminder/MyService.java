package com.jikexueyuan.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import java.util.Calendar;

public class MyService extends Service {

    public static final int NOTIFICATION_ID = 1400;
    Db db = new Db(this);
    SQLiteDatabase dbReader;
    private Cursor c;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("开始");
        new Thread(){
            @Override
            public void run() {
                super.run();

                dbReader = db.getReadableDatabase();
                c = dbReader.query("list",null,null,null,null,null,null);

                while (c.moveToNext()){
                    int time = Integer.parseInt(c.getString(c.getColumnIndex("time")));
                    String thing = c.getString(c.getColumnIndex("thing"));
                    Calendar calendar = Calendar.getInstance();
                    int locTime = calendar.get(Calendar.HOUR_OF_DAY);
                    System.out.println("时间是："+locTime);
                    if(locTime == time){
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyService.this)
                                .setSmallIcon(R.drawable.clock)
                                .setContentTitle("时间"+ time +":00")
                                .setContentText("提醒事项:" + thing);
                        Notification notification = builder.build();
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(NOTIFICATION_ID,notification);
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        c.close();
    }
}
