package com.example.autoattendance;

import java.util.List;

public class Student extends BaseEntity {
    public String FirstName;
    public String LastName;
    public String StudentNumber;
    public String Password;
    public int Year;
    public String Email;
    public boolean Enrolled;

    public int ProgramId;
    public Program Program;
    public List<Attendance> Attendance;

    public Student(String firstName, String lastName, String email, String studentNumber, String password, int Year,
                   int programId) {
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        Password = password;
        ProgramId = programId;
        this.Year = Year;
        StudentNumber = studentNumber;
    }
}
