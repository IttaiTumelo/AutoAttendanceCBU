package com.example.autoattendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ApprovalAdapter extends  RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder> {

    Context context;
    ArrayList<Course> approvalArrayList; //TODO : filter by student
    public ApprovalAdapter(Context context, ArrayList<Course> approvalArrayList) {
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
        Course course = approvalArrayList.get(position);
        holder.courseName.setText(course.getName());
//        holder.courseCode.setText(course.);
//        holder.courseCode.setText(course.getCourseCode());
//        holder.courseInstructor.setText(course.getCourseInstructor());
//        holder.approveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_approvalFragment_to_studentInClassFragment);
//            }
//        });
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
        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.text_view_course_name);
//            courseCode = itemView.findViewById(R.id.text_view_course_code);
//            courseInstructor = itemView.findViewById(R.id.text_view_course_lecturer);
//            approveButton = itemView.findViewById(R.id.button_request_approval);
        }
    }
}
