package com.example.autoattendance;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity {
    // The timetable that this attendance record belongs to

    @SerializedName("body")
    private String text;
    public Date Date ;
    // The student who this attendance record belongs to
    public List<Student> StudentsInClass ;
    //course in charge of the attendance
    public Lecture Lecture ;
    public int LectureId ;

    public Attendance(Date date, List<Student> studentsInClass, Lecture lecture, int lectureId) {
        Date = date;
        StudentsInClass = studentsInClass;
        Lecture = lecture;
        LectureId = lectureId;
    }
}
