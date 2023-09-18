package com.example.autoattendance.Adapter;

import static com.example.autoattendance.API.BaseStatics.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Entities.Student;
import com.example.autoattendance.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovalAdapter extends  RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder> {

    Context context;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    ArrayList<Student> approvalArrayList; //TODO : filter by student
    public ApprovalAdapter(Context context, ArrayList<Student> approvalArrayList) {
        this.context = context;
        this.approvalArrayList = approvalArrayList;

    }

    @NonNull
    @Override
    public ApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_lecturer_item_rf, parent, false);
        return new ApprovalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalViewHolder holder, int position) {
        Student student = approvalArrayList.get(position);
        holder.courseName.setText(student.getName());
//        holder.courseCode.setText(course.);
//        holder.courseCode.setText(course.getCourseCode());
//        holder.courseInstructor.setText(course.getCourseInstructor());
//        holder.approveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_approvalFragment_to_studentInClassFragment);
//            }
//        });
        holder.approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendanceApi.acceptStudent(student.id).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if(response.isSuccessful()){
//                            Navigation.findNavController(v).navigate(R.id.action_approvalFragment_to_studentInClassFragment);
                            Toast.makeText(context, "Student Accepted", Toast.LENGTH_SHORT).show();
                            approvalArrayList.remove(student);
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            approvalArrayList.remove(student);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceApi.rejectStudent(student.id).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Student Rejected", Toast.LENGTH_SHORT).show();
                            approvalArrayList.remove(student);
                            notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(context, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                            approvalArrayList.remove(student);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        if(approvalArrayList!=null)
        return approvalArrayList.size();
        else return 0;
    }

    public static class ApprovalViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        TextView courseCode;
        TextView courseInstructor;
        Button approveButton;
        Button rejectButton;
        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.text_view_course_name);
//            courseCode = itemView.findViewById(R.id.text_view_course_code);
//            courseInstructor = itemView.findViewById(R.id.text_view_course_lecturer);
//            approveButton = itemView.findViewById(R.id.button_request_approval);
            approveButton = itemView.findViewById(R.id.button_accept);
            rejectButton = itemView.findViewById(R.id.button_reject);
        }
    }
}
