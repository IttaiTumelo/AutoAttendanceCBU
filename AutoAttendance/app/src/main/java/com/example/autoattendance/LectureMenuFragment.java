package com.example.autoattendance;


import static com.example.autoattendance.BaseBluetoothThread.*;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
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

import com.example.autoattendance.databinding.FragmentLectureMenuBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LectureMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LectureMenuFragment extends Fragment {

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

        binding.buttonStudentApprovals.setOnClickListener(v -> {
            NavHostFragment.findNavController(LectureMenuFragment.this).navigate(R.id.action_lectureMenuFragment_to_approvalFragment);
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
                    break;
            }
            return true;
        }
    });
}