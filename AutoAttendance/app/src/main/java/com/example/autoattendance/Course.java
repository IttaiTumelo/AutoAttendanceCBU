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

    @SerializedName("body")
    private String text;

    public int MinYear;
    public List<Lecture> Lectures;
    public int ProgramId;
    public Program Program;

    public Course(int minYear, List<Lecture> lectures, int programId, com.example.autoattendance.Program program) {
        MinYear = minYear;
        Lectures = lectures;
        ProgramId = programId;
        Program = program;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMinYear() {
        return MinYear;
    }

    public void setMinYear(int minYear) {
        MinYear = minYear;
    }

    public List<Lecture> getLectures() {
        return Lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        Lectures = lectures;
    }

    public int getProgramId() {
        return ProgramId;
    }

    public void setProgramId(int programId) {
        ProgramId = programId;
    }

    public com.example.autoattendance.Program getProgram() {
        return Program;
    }

    public void setProgram(com.example.autoattendance.Program program) {
        Program = program;
    }
}
