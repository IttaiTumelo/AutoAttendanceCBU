package com.example.autoattendance;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
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

    public List<LectureTime> LectureTimetable;
    public String Instructor;
    public Course Course;
    public List<Attendance> Attendance;

    public Lecture(List<LectureTime> lectureTimetable, String instructor, Course course, List<Attendance> attendance) {
        LectureTimetable = lectureTimetable;
        Instructor = instructor;
        Course = course;
        Attendance = attendance;
    }
}
