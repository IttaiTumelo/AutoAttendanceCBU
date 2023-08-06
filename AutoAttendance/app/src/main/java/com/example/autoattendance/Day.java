package com.example.autoattendance;

import java.util.List;

public class Day extends BaseEntity{
    public int Name;
    // The list of lectures for this day
    public List<LectureTime> Lectures;
}
