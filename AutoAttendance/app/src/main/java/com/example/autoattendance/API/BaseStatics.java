package com.example.autoattendance.API;

import android.util.Log;

import java.util.List;

import lombok.val;
import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseStatics {
    public static Retrofit retrofit = new Retrofit
            .Builder().baseUrl("https://r3hdh79h-7280.uks1.devtunnels.ms/api/")
            .addConverterFactory(GsonConverterFactory.create()).build();
//    public  AttendanceApi attendanceApi = retrofit.create(AttendanceApi.class);
//    public List<Course> courses;
//    Call<List<Course>> call = attendanceApi.getCourses();
//    public List<Course> getCourses(){
//        call.enqueue(new Callback<List<Course>>() {
//            @Override
//            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
//                if(!response.isSuccessful()){
//                    throw new RuntimeException("Not successful "+ response.code());
//                }
//                Log.d("TAG", "onResponse: " + response.body().toString());
//                courses = response.body();
////                return;
////                for(Course course : courses){
////
////                }
//            }
//            @Override
//            public void onFailure(Call<List<Course>> call, Throwable t) {
//                throw new RuntimeException("Exception: " + t.getMessage());
//            }
//        });
//        return courses;
//    }
}
