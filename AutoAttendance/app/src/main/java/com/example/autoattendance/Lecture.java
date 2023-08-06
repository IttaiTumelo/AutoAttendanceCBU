package com.example.autoattendance;

import java.util.Date;
import java.util.List;

public class Lecture extends BaseEntity {
    public List<LectureTime> lectureTimeTable;
    public List<TimeTable> timeTables;
    public String Instructor;
    public List<Date> LectureDates;
    public Course Course;
    public List<Student> Students;
    public List<Attendance> Attendance;



}
