package com.example.autoattendance;


import static com.example.autoattendance.BaseBluetoothThread.*;
import static com.example.autoattendance.API.BaseStatics.retrofit;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Entities.Lecture;
import com.example.autoattendance.databinding.FragmentLectureMenuBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LectureMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LectureMenuFragment extends Fragment {

    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SendReceive sendReceive;
    private FragmentLectureMenuBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice[] btArray;

    public LectureMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LectureMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LectureMenuFragment newInstance(String param1, String param2) {
        LectureMenuFragment fragment = new LectureMenuFragment();
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
        binding = FragmentLectureMenuBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.fragment_lecture_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ///////////////////////////////////////////////////////////////////////////////////from the in class

//        attendanceApi.getCurrentAttendanceId().enqueue(new retrofit2.Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if(response.isSuccessful()) {
//                    int attendanceId = response.body();
//                    if(attendanceId < 1 ) {
//                        binding.btnAddClassRegister.setText("Start Class");
//                        binding.btnAddClassRegister.setEnabled(true);
//                        return;
//                    }
//                    attendanceApi.getAttendanceById(attendanceId).enqueue(new retrofit2.Callback<Attendance>() {
//                        @Override
//                        public void onResponse(Call<Attendance> call, Response<Attendance> response) {
//                            if(response.isSuccessful()) {
//                                Attendance attendance = response.body();
//                                if(attendance.StudentsInClass == null) attendance.StudentsInClass = new ArrayList<>();
//                                attendanceApi.completeCourse().enqueue(new retrofit2.Callback<Course>() {
//                                    @Override
//                                    public void onResponse(Call<Course> call, Response<Course> response) {
//                                        if(response.isSuccessful()) {
//                                            Course course = response.body();
//                                            Toast.makeText(getContext(), "Course Obtained", Toast.LENGTH_SHORT).show();
//
//                                            Log.d("TAG", "onResponse: " + course.program.students.size());
//                                            ClassRegistrationAdapter classRegistrationAdapter = new ClassRegistrationAdapter(getContext(), course, attendance);
//                                            binding.studentRvClassRegister.setAdapter(classRegistrationAdapter);
//                                            classRegistrationAdapter.notifyDataSetChanged();
//                                        }
//                                        else {
//                                            Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                    @Override
//                                    public void onFailure(Call<Course> call, Throwable t) {
//                                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//                            else {
//                                Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<Attendance> call, Throwable t) {
//                            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        binding.studentRvClassRegister.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.studentRvClassRegister.setHasFixedSize(true);
//        binding.btnAddClassRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//
//                attendanceApi.createAttendance(new Attendance(null, null, null, 1  )).enqueue(new retrofit2.Callback<Attendance>() {
//                    @Override
//                    public void onResponse(Call<Attendance> call, Response<Attendance> response) {
//                        if (response.isSuccessful()) {
//                            Attendance attendance = response.body();
//
//                            attendanceApi.completeCourse().enqueue(new retrofit2.Callback<Course>() {
//                                @Override
//                                public void onResponse(Call<Course> call, Response<Course> response) {
//                                    if(response.isSuccessful()) {
//                                        Course course = response.body();
//                                        Toast.makeText(getContext(), "Register Created", Toast.LENGTH_SHORT).show();
//
//
//                                    }
//                                    else {
//                                        Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<Course> call, Throwable t) {
//
//                                }
//                            });
//
//                        } else {
//                            Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Attendance> call, Throwable t) {
//                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        ///////////////////////////////////////////////////////////////////////////////////from the in class




        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_CALENDAR);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_CALENDAR)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_CALENDAR},
                        2);

                // MY_PERMISSIONS_REQUEST_WRITE_CALENDAR is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        binding.btnSendRegister.setOnClickListener(v -> {
            binding.btnSendRegister.setEnabled(false);
            String email = binding.etEmail.getText().toString();
            if(email.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an email", Toast.LENGTH_SHORT).show();
                return;
            }
            attendanceApi.sendMail(email).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful())
                        Toast.makeText(getContext(), "Email Sent", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.buttonStudentApprovals.setOnClickListener(v -> {
            NavHostFragment.findNavController(LectureMenuFragment.this).navigate(R.id.action_lectureMenuFragment_to_approvalFragment);
        });
        binding.btnCheckTimetable.setOnClickListener(v -> {
            NavHostFragment.findNavController(LectureMenuFragment.this).navigate(R.id.action_lectureMenuFragment_to_tableFragment);
        });
        binding.buttonCurrentStudents.setOnClickListener(v -> {
            NavHostFragment.findNavController(LectureMenuFragment.this).navigate(R.id.action_lectureMenuFragment_to_classRegisterFragment);
        });
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        binding.materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Enable Bluetooth and Vibrate
//                mBluetoothAdapter.enable();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 7);
                for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
                    String addrress = device.getAddress();
                    String name = device.getName();
                    if (name.charAt(0) == 'I')
                        Toast.makeText(getActivity(), name + " " + addrress, Toast.LENGTH_SHORT).show();
                }

                ServerClass serverClass = new ServerClass(bluetoothAdapter, getContext(), binding.textViewLectureMenuBluetoothStatus, handler);
                serverClass.start();
            } else {
                // Disable Bluetooth and Vibrate
//                bluetoothAdapter.disable();
            }
        });






    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            TextView status = binding.textViewLectureMenuBluetoothStatus;
            switch (msg.what)
            {
                case STATE_LISTENING:
                    status.setText("Listening");
                    break;
                case STATE_CONNECTING:
                    status.setText("Connecting");
                    break;
                case STATE_CONNECTED:
                    status.setText("Connected");
                    break;
                case STATE_CONNECTION_FAILED:
                    status.setText("Connection Failed");
                    break;
                case STATE_MESSAGE_RECEIVED:
                    byte[] readBuff= (byte[]) msg.obj;
                    String tempMsg=new String(readBuff,0,msg.arg1);
                    binding.textViewBluetoothMsg.setText(tempMsg);
                    int studentId = Integer.parseInt(tempMsg);
                    attendanceApi.markAttendance(studentId).enqueue(new Callback<Lecture>() {
                        @Override
                        public void onResponse(Call<Lecture> call, Response<Lecture> response) {
                            Toast.makeText(getContext(), "Attendance Marked", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Lecture> call, Throwable t) {
                            Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
            }
            return true;
        }
    });
}