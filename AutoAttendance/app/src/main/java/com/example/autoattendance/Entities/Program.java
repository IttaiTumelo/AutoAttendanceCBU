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
public class Program extends BaseEntity {

    @SerializedName("body")
    private String text;
    public List<Course> courses;
    public List<Student> students;
    public int schoolId;

    public Program(List<Course> courses, List<Student> students, int schoolId) {
        this.courses = courses;
        this.students = students;
        this.schoolId = schoolId;
    }
}
