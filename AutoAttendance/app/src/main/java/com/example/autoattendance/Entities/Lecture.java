package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecture extends BaseEntity {
    @SerializedName("body")
    private String text;
    public List<LectureTime> lectureTimetable;
    public String instructor;
    public int courseId;
    public Course course;
    public String date;
    public List<Student> studentsInClass;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Lecture(int courseId) {
        this.lectureTimetable = new ArrayList<>();
        this.instructor = "";
        this.courseId = courseId;
        this.studentsInClass = new ArrayList<>();
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LectureTime> getLectureTimetable() {
        return lectureTimetable;
    }

    public void setLectureTimetable(List<LectureTime> lectureTimetable) {
        this.lectureTimetable = lectureTimetable;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public List<Student> getStudentsInClass() {
        return studentsInClass;
    }

    public void setStudentsInClass(List<Student> studentsInClass) {
        this.studentsInClass = studentsInClass;
    }
}
