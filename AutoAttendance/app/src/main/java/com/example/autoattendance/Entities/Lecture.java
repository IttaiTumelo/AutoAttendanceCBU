package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;
import com.google.gson.annotations.SerializedName;

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
    public Course course;
    public List<Attendance> attendance;

    public Lecture(List<LectureTime> lectureTimetable, String instructor, Course course, List<Attendance> attendance) {
        this.lectureTimetable = lectureTimetable;
        this.instructor = instructor;
        this.course = course;
        this.attendance = attendance;
    }
}
