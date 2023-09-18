package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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
//    public Date date ;
    // The student who this attendance record belongs to
    public List<Student> studentsInClass;
    //course in charge of the attendance
    public Lecture lecture ;
    public int lectureId ;

    public Attendance( List<Student> studentsInClass, Lecture lecture, int lectureId) {
//        this.date = date;
        this.studentsInClass = studentsInClass;
        this.lecture = lecture;
        this.lectureId = lectureId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public List<Student> getStudentsInClass() {
        return studentsInClass;
    }

    public void setStudentsInClass(List<Student> studentsInClass) {
        this.studentsInClass = studentsInClass;
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
