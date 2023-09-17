package com.example.autoattendance;

import static com.example.autoattendance.BaseStatics.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.*;
import lombok.var;

public class ClassRegistrationAdapter extends  RecyclerView.Adapter<ClassRegistrationAdapter.ClassRegistrationViewHolder> {

    Context context;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    List<Student> RegisteredStudents = new ArrayList<Student>();
    List<Student> StudentsInClass = new ArrayList<Student>();

    Course course; //TODO : filter by student
    Attendance attendance; //TODO : filter by student
    public ClassRegistrationAdapter(Context context, Course course, Attendance attendance) {
        this.context = context;
        this.course = course;

        this.RegisteredStudents = course.Program.Students;
        this.StudentsInClass = attendance.StudentsInClass;

    }
    @NonNull
    @Override
    public ClassRegistrationAdapter.ClassRegistrationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_ic_rf, parent, false);
        return new ClassRegistrationAdapter.ClassRegistrationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassRegistrationAdapter.ClassRegistrationViewHolder holder, int position) {
        Student student = RegisteredStudents.get(position);
        holder.studentName.setText(student.getName());
        if (StudentsInClass.contains(student)) {
            holder.studentPresence.setText("Present");
        } else {
            holder.studentPresence.setText("Absent");
        }
    }

    @Override
    public int getItemCount() {
        if (RegisteredStudents != null)
            return RegisteredStudents.size();
        else
        return 0;
    }

    public class ClassRegistrationViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView studentPresence;
        public ClassRegistrationViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.tv_ic_rf);
            studentPresence = itemView.findViewById(R.id.tv_ic_tc);
        }
    }
}
