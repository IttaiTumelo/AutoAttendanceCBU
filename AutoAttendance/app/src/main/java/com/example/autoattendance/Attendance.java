package com.example.autoattendance;

import java.util.Date;
import java.util.List;

public class Attendance extends BaseEntity {
    // The timetable that this attendance record belongs to
    public java.util.Date Date ;
    // The student who this attendance record belongs to
    public List<Student> StudentsInClass ;
    //course in charge of the attendance
    public Lecture Lecture ;
}
