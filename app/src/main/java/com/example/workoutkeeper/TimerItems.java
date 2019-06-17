package com.example.workoutkeeper;

import android.os.CountDownTimer;

public class TimerItems {
    private String Minute;
    private String Second;
    private Integer reset;
    private CountDownTimer cdt;
    public TimerItems(String Minute, String Second){
        this.Minute = Minute;
        this.Second = Second;

    }

    String getMinute(){return Minute;}

    public void setMinute(String minute){
        this.Minute = minute;

    }
    String getSecond(){return Second;}

    public void setSecond(String second){
        this.Second = second;

    }
    CountDownTimer getCdt(){return cdt;}

    public void setCdt(CountDownTimer cdt){
        this.cdt = cdt;

    }

    Integer getReset(){return reset;}

    public void setReset(Integer reset){
        this.reset = reset;

    }

}
