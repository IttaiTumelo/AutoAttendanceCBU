package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;

import java.util.Date;

public class LectureTime extends BaseEntity {
    public int dayOfWeek;
    public Date startTime;
    public Date endTime;
    public Lecture lecture;
}
