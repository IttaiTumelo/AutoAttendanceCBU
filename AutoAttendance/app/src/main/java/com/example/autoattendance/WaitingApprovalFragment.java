package com.example.autoattendance;

import static com.example.autoattendance.BaseStatics.retrofit;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingApprovalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingApprovalFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaitingApprovalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingApprovalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingApprovalFragment newInstance(String param1, String param2) {
        WaitingApprovalFragment fragment = new WaitingApprovalFragment();
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
        AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);
        SharedPreferences sharedPreferences = getActivity().getPreferences(getContext().MODE_PRIVATE);
        ((MainActivity)getActivity()).StoreData("approved", false);
        int studentId = sharedPreferences.getInt("studentId", -1);
        if(studentId != -1) {

            attendanceApi.getStudentById(studentId).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if(response.isSuccessful()){
                        Student student = response.body();
                        if(student != null) {
                            if(student.isApproved()) {
                                ((MainActivity)getActivity()).StoreData("approved", true);
                                Toast.makeText(getContext(), "Student Approved", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                ((MainActivity)getActivity()).StoreData("approved", false);
                                Toast.makeText(getContext(), "Student Not Approved", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
//                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_approvalFragment);
                        }

                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }

        return inflater.inflate(R.layout.fragment_waiting_approval, container, false);


    }
}