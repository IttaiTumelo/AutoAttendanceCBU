package com.example.autoattendance.API;

import com.example.autoattendance.Entities.Course;
import com.example.autoattendance.Entities.Lecture;
import com.example.autoattendance.Entities.Program;
import com.example.autoattendance.Entities.Schedule;
import com.example.autoattendance.Entities.Student;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AttendanceApi {
    @GET("course")
    Call<List<Course>> getCourses();

    @GET("program")
    Call<List<Program>> getProgarms();

    @POST("student")
    Call<Student> registerStudent(@Body Student student);

    @GET("student/sin/{id}")
    Call<Student> getStudentBySIN(@Path("id") String id);

    @POST("AddStudent{sid}toProgram{pid}")
    Call registerStudentToProgram(@Path("sid") String sid, @Path("pid") int pid);

    @GET("student/{id}")
    Call<Student> getStudentById(@Path("id") int id);

    @GET("student")
    Call<List<Student>> getStudents();

    @GET("student/accept/{id}")
    Call<Student> acceptStudent(@Path("id") int id);

    @GET("student/reject/{id}")
    Call<Student> rejectStudent(@Path("id") int id);

    @GET("course/complete/1")
    Call<Course> completeCourse();

    @POST("Attendance")
    Call<Lecture> createAttendance(@Body Lecture attendance);

    @GET("Attendance/current")
    Call<Integer> getCurrentLectureId();

    @GET("Attendance/{id}")
    Call<Lecture> getAttendanceById(@Path("id") int id);

    @GET("Attendance/markPresent/{id}")
    Call<Lecture> markAttendance(@Path("id") int id);

    @GET("Attendance/course/1/sendMail/{address}")
    Call<String> sendMail(@Path("address") String address);
    @GET("Schedule/program/{programId}")
    Call<ArrayList<Schedule>> getSchedule(@Path("programId") int programId );
//getStudentBySIN
//    @GET("")
}
