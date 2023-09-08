package com.example.autoattendance;

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
    Call<Student> getStudent(@Path("id") String id);
//
//    @GET("")
}
