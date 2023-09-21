package com.example.autoattendance;

import static com.example.autoattendance.API.BaseStatics.retrofit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Adapter.TableAdapter;
import com.example.autoattendance.Entities.Schedule;
import com.example.autoattendance.databinding.FragmentApprovalBinding;
import com.example.autoattendance.databinding.FragmentTableBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TableFragment extends Fragment {
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    FragmentTableBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TableFragment newInstance(String param1, String param2) {
        TableFragment fragment = new TableFragment();
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
//        return inflater.inflate(R.layout.fragment_table, container, false);
        binding = FragmentTableBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerViewLecturerX.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewLecturerX.setHasFixedSize(true);
        attendanceApi.getSchedule(1).enqueue(new Callback<ArrayList<Schedule>>() {
            @Override
            public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Schedule> scheduleList = response.body();
                TableAdapter tableAdapter = new TableAdapter(getContext(), scheduleList);
                binding.recyclerViewLecturerX.setAdapter(tableAdapter);
                tableAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Success: " + response.code(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}