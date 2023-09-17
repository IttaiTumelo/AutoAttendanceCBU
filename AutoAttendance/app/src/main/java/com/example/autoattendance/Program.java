package com.example.autoattendance;

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
public class Program extends BaseEntity {

    @SerializedName("body")
    private String text;
    public List<Course> Courses;
    public List<Student> Students;
    public int SchoolId;

    public Program(List<Course> courses, List<Student> students, int schoolId) {
        Courses = courses;
        Students = students;
        SchoolId = schoolId;
    }
}
