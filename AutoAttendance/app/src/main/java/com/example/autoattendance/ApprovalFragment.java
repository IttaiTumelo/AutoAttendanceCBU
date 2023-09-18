package com.example.autoattendance;

import static com.example.autoattendance.API.BaseStatics.*;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Adapter.ApprovalAdapter;
import com.example.autoattendance.Entities.Course;
import com.example.autoattendance.Entities.Student;
import com.example.autoattendance.databinding.FragmentApprovalBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApprovalFragment extends Fragment {
    public int sid = 0, pid = 0;
    SharedPreferences sharedPreferences;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Course> courseList;
    private ArrayList<Student> studentList;
    FragmentApprovalBinding binding;

    public ApprovalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApprovalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApprovalFragment newInstance(String param1, String param2) {
        ApprovalFragment fragment = new ApprovalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentApprovalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        boolean approved = sharedPref.getBoolean("approved", false);
//        String studentNumber = sharedPref.getString("studentNumber", "");
//        int programId = sharedPref.getInt("programId", 0);

        binding.recyclerViewLecturerCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewLecturerCourses.setHasFixedSize(true);

        attendanceApi.getStudents().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                studentList = new ArrayList<>(response.body());
                Log.d("TAG", "onResponse: " + studentList.size());
                studentList = studentList.stream().filter(student -> student.isApproved == false).collect(Collectors.toCollection(ArrayList::new));
                ApprovalAdapter approvalAdapter = new ApprovalAdapter(getContext(), studentList);
                binding.recyclerViewLecturerCourses.setAdapter(approvalAdapter);
                approvalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}