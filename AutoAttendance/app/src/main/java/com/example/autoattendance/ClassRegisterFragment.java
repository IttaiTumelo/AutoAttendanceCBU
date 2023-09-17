package com.example.autoattendance;

import static com.example.autoattendance.BaseStatics.retrofit;

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

import com.example.autoattendance.databinding.FragmentClassRegisterBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClassRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClassRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    FragmentClassRegisterBinding binding;

    public ClassRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClassRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClassRegisterFragment newInstance(String param1, String param2) {
        ClassRegisterFragment fragment = new ClassRegisterFragment();
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
        binding = FragmentClassRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnAddClassRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.studentRvClassRegister.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.studentRvClassRegister.setHasFixedSize(true);



                attendanceApi.createAttendance(new Attendance(null, null, null, 1  )).enqueue(new Callback<Attendance>() {
                    @Override
                    public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                        if (response.isSuccessful()) {
                            Attendance attendance = response.body();

                            attendanceApi.completeCourse().enqueue(new Callback<Course>() {
                                @Override
                                public void onResponse(Call<Course> call, Response<Course> response) {
                                    if(response.isSuccessful()) {
                                        Course course = response.body();
                                        Toast.makeText(getContext(), "Course Completed", Toast.LENGTH_SHORT).show();

                                        Log.d("TAG", "onResponse: " + course.Program.Students.size());
                                        ClassRegistrationAdapter classRegistrationAdapter = new ClassRegistrationAdapter(getContext(), course, attendance);
                                        binding.studentRvClassRegister.setAdapter(classRegistrationAdapter);
                                        classRegistrationAdapter.notifyDataSetChanged();
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Course> call, Throwable t) {

                                }
                            });

                        } else {
                            Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Attendance> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}