package com.example.autoattendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("course/complete/6")
    Call<Course> completeCourse();

    @POST("Attendance")
    Call<Attendance> createAttendance(@Body Attendance attendance);
//getStudentBySIN
//    @GET("")
}
