package com.example.autoattendance;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntity {
    public int minYear;

    @SerializedName("body")
    private String text;

    public List<Lecture> Lectures;

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Lecture> getLectures() {
        return Lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        Lectures = lectures;
    }

    //    private String courseName;
//    private String courseCode;
//    private String courseInstructor;
//    private String courseTime;
//    private String courseLocation;
//    private String courseDate;
//    private String courseStatus;
//
//    public Course(String courseName, String courseCode, String courseInstructor, String courseTime, String courseLocation, String courseDate, String courseStatus) {
//        this.courseName = courseName;
//        this.courseCode = courseCode;
//        this.courseInstructor = courseInstructor;
//        this.courseTime = courseTime;
//        this.courseLocation = courseLocation;
//        this.courseDate = courseDate;
//        this.courseStatus = courseStatus;
//    }
//
//    public Course() {
//    }
//
//
//
//    public String getCourseName() {
//        return courseName;
//    }
//
//    public String getCourseCode() {
//        return courseCode;
//    }
//
//    public String getCourseInstructor() {
//        return courseInstructor;
//    }
//
//    public String getCourseTime() {
//        return courseTime;
//    }
//
//    public String getCourseLocation() {
//        return courseLocation;
//    }
//
//    public String getCourseDate() {
//        return courseDate;
//    }
//
//    public String getCourseStatus() {
//        return courseStatus;
//    }
}
