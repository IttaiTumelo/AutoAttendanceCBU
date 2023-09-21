package com.example.autoattendance.Entities;

public class TimeSlot {
    public int id ;
    public String startTime ;
    public String endTime ;
    public String duration ;

    public TimeSlot() {
    }

    public TimeSlot(String startTime, String endTime, String duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
