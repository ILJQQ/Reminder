package com.jikexueyuan.reminder;

/**
 * Created by huyiqing on 2016/11/23.
 */

public class Reminder {
    private String thing;
    private int time;

    public Reminder(){

    }

    public Reminder(String thing, int time){
        this.thing = thing;
        this.time = time;
    }

    public void setThing(String thing){
        this.thing = thing;
    }

    public String getThing(){
        return thing;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

    public String toString(){
        return "事件的时间是：" + time + "内容是: " + thing;
    }
}
