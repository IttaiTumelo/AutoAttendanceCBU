package com.example.autoattendance;

import java.util.List;

public class TimeTable extends BaseEntity{
    //Class owning this time table
    public Student Student;//TODO: Change class to accomodate lectures
    public List<Day> Week;
    // The list of days for this schedule
}
