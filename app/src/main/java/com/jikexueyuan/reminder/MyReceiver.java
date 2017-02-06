package com.jikexueyuan.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    private static final String ACTION = "Android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent service = new Intent(Intent.ACTION_RUN);
            service.setClass(context, MyService.class);
            context.startService(service);
            Log.v("tag","成功监听到开机并启动服务");
        }
    }
}
