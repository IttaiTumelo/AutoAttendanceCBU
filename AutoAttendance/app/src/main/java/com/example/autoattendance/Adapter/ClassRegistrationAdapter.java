package com.example.autoattendance.Adapter;

import static com.example.autoattendance.API.BaseStatics.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Entities.Course;
import com.example.autoattendance.Entities.Lecture;
import com.example.autoattendance.Entities.Student;
import com.example.autoattendance.R;

import java.util.ArrayList;
import java.util.List;

public class ClassRegistrationAdapter extends  RecyclerView.Adapter<ClassRegistrationAdapter.ClassRegistrationViewHolder> {

    Context context;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    List<Student> RegisteredStudents = new ArrayList<Student>();
    List<Student> StudentsInClass = new ArrayList<Student>();

    Course course; //TODO : filter by student
    Lecture attendance; //TODO : filter by student
    public ClassRegistrationAdapter(Context context, Course course, Lecture attendance) {
        this.context = context;
        this.course = course;

        this.RegisteredStudents = course.program.students;
        this.StudentsInClass = attendance.studentsInClass;
        this.attendance = attendance;
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
        //if attendance.studentsInClass list contains a student with student.StudentId then set the text to ✔ else ❌
        holder.studentPresence.setText(
                attendance.studentsInClass.stream()
                        .anyMatch(s -> s.studentNumber.equals(student.studentNumber)) ? "✔" : " X"
        );

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
