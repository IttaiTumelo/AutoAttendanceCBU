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
import com.example.autoattendance.Entities.Schedule;
import com.example.autoattendance.Entities.Student;
import com.example.autoattendance.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableAdapter extends  RecyclerView.Adapter<TableAdapter.TableViewHolder> {

        Context context;

    ArrayList<Schedule> table;
    ArrayList<List<Schedule>> days =  new ArrayList<>();
    public TableAdapter(Context context, ArrayList<Schedule> table) {
        this.context = context;
        this.table = table;
        days = table.stream().collect(Collectors.groupingBy(s -> s.weekDay)).entrySet().stream().map(e -> e.getValue()).collect(Collectors.toCollection(ArrayList::new));

    }
    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.table_item_rl, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.TableViewHolder holder, int position) {
        List<Schedule> scheduleList = days.get(position);
        holder.tvDay.setText(position == 0 ? "Monday" : position == 1 ? "Tuesday" : position == 2 ? "Wednesday" : position == 3 ? "Thursday" : "Friday");
        //filter the tables
//        List<Schedule> scheduleList = table.stream().filter(s -> s.weekDay == position).collect(Collectors.toList());
        if(scheduleList.size() > 0)
            holder.tvCourseName1.setText(scheduleList.get(0).course.name);
        else holder.tvCourseName1.setText("No Course");
        if(scheduleList.size() > 1)
            holder.tvCourseName2.setText(scheduleList.get(1).course.name);
        else holder.tvCourseName1.setText("No Course");
        if(scheduleList.size() > 2)
            holder.tvCourseName3.setText(scheduleList.get(2).course.name);
        else holder.tvCourseName1.setText("No Course");

    }

    @Override
    public int getItemCount() {
        return days.size();

    }

    public class TableViewHolder extends RecyclerView.ViewHolder {
                TextView tvDay;
        TextView tvCourseName1;
        TextView tvCourseName2;
        TextView tvCourseName3;
        public TableViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDay = itemView.findViewById(R.id.tv_day);
            tvCourseName1 = itemView.findViewById(R.id.tvCourseName1);
            tvCourseName2 = itemView.findViewById(R.id.tvCourseName2);
            tvCourseName3 = itemView.findViewById(R.id.tvCourseName3);
        }
    }


//
//    @Override
//    public void onBindViewHolder(@NonNull TableAdapter.TableViewHolder holder, int position) {
//        holder.tvCourseName1.setText("scheduleList.get(0).course.name");
//
////        Schedule student = table.get(position);
////        holder.tvDay.setText(position == 1 ? "Monday" : position == 2 ? "Tuesday" : position == 3 ? "Wednesday" : position == 4 ? "Thursday" : "Friday");
////        //filter the tables
////        List<Schedule> scheduleList = table.stream().filter(s -> s.weekDay == position).collect(Collectors.toList());
////        if(scheduleList.size() > 0)
////            holder.tvCourseName1.setText(scheduleList.get(0).course.name);
////        else holder.tvCourseName1.setText("No Course");
////        if(scheduleList.size() > 1)
////            holder.tvCourseName2.setText(scheduleList.get(1).course.name);
////        else holder.tvCourseName1.setText("No Course");
////        if(scheduleList.size() > 2)
////            holder.tvCourseName3.setText(scheduleList.get(2).course.name);
////        else holder.tvCourseName1.setText("No Course");
//
//    }


//
//    public static class TableViewHolder extends RecyclerView.ViewHolder {
//        TextView tvDay;
//        TextView tvCourseName1;
//        TextView tvCourseName2;
//        TextView tvCourseName3;
//
//        public TableViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvDay = itemView.findViewById(R.id.tv_day);
//            tvCourseName1 = itemView.findViewById(R.id.tvCourseName1);
//            tvCourseName2 = itemView.findViewById(R.id.tvCourseName2);
//            tvCourseName3 = itemView.findViewById(R.id.tvCourseName3);
//        }
//    }

}
