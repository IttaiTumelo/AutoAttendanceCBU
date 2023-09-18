package com.example.autoattendance.Entities;

import com.example.autoattendance.BaseEntity;
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

    @SerializedName("body")
    private String text;

    public int minYear;
    public List<Lecture> lectures;
    public int programId;
    public Program program;

    public Course(int minYear, List<Lecture> lectures, int programId, Program program) {
        this.minYear = minYear;
        this.lectures = lectures;
        this.programId = programId;
        this.program = program;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public List<Lecture> getLectures() {
        return this.lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public int getProgramId() {
        return this.programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public Program getProgram() {
        return this.program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
