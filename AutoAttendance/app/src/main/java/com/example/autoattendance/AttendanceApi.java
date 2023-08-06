package com.example.autoattendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AttendanceApi {
    @GET("Course")
    Call<List<Course>> getCourses();
}
