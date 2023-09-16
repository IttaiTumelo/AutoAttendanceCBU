package com.example.autoattendance;

import static com.example.autoattendance.BaseStatics.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingApprovalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_approval);

        AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        boolean approved = sharedPref.getBoolean("approved", false);
        int studentId = sharedPref.getInt("studentId", -1);

        if(studentId == -1){

            Toast.makeText(this, "Error getting student id", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "student id is " + studentId, Toast.LENGTH_SHORT).show();
            attendanceApi.getStudentById(studentId).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if(response.isSuccessful()){
                        Student student = response.body();
                        if(student.isApproved)
                            Toast.makeText(PendingApprovalActivity.this, "You are approved", Toast.LENGTH_SHORT).show();
                        if(student.programId == 0){
                            Toast.makeText(PendingApprovalActivity.this, "You are not registered to any program", Toast.LENGTH_SHORT).show();
//                        NavHostFragment.findNavController(PendingApprovalActivity.this).navigate(R.id.action_pendingApprovalFragment_to_loginFragment);
                        }else{
                            Toast.makeText(PendingApprovalActivity.this, "You are registered to program " + student.programId, Toast.LENGTH_SHORT).show();
//                        NavHostFragment.findNavController(PendingApprovalActivity.this).navigate(R.id.action_pendingApprovalFragment_to_mainAppActivity);
                        }
                    }else{
                        Toast.makeText(PendingApprovalActivity.this, "Error getting student", Toast.LENGTH_SHORT).show();
                        Log.d("PendingApprovalActivity", "onResponse: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(PendingApprovalActivity.this, "did not even try getting student", Toast.LENGTH_SHORT).show();
                    Log.d("PendingApprovalActivity", "onFailure: " + t.getMessage());

                }
            });

        }

    }
}