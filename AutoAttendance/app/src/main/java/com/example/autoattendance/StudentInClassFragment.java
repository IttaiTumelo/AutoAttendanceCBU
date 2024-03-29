package com.example.autoattendance;

import static com.example.autoattendance.API.BaseStatics.retrofit;
import static com.example.autoattendance.BaseBluetoothThread.*;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoattendance.API.AttendanceApi;
import com.example.autoattendance.Adapter.TableAdapter;
import com.example.autoattendance.Entities.DataType;
import com.example.autoattendance.Entities.Schedule;
import com.example.autoattendance.databinding.FragmentStudentInClassBinding;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentInClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentInClassFragment extends Fragment {
    View view;
    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;
    private boolean authenticationPassed = false;
    private boolean authenticationCompleted = false;
    private String authenticationMessage = "";
    private FragmentStudentInClassBinding binding;
    BluetoothAdapter bluetoothAdapter;
    ClientClass clientClass;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentInClassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudentInClassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentInClassFragment newInstance(String param1, String param2) {
        StudentInClassFragment fragment = new StudentInClassFragment();
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
        // Inflate te layout for this fragment
        binding = FragmentStudentInClassBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        ((MainActivity)getActivity()).StoreData("approved", true);

        binding.recyclerViewTable.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewTable.setHasFixedSize(true);

//        attendanceApi.getSchedule(1).enqueue(new Callback<ArrayList<Schedule>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Schedule>> call, Response<ArrayList<Schedule>> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(getContext(), "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                ArrayList<Schedule> scheduleList = response.body();
//                TableAdapter tableAdapter = new TableAdapter(getContext(), scheduleList);
//                binding.recyclerViewTable.setAdapter(tableAdapter);
//                tableAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), "Success: " + response.code(), Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Schedule>> call, Throwable t) {
//                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });


        binding.buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewStatus.setText("Attempting to connect...");



                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice bluetoothDevice = null;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    // ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    // int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
                    Toast.makeText(getContext(), "Device: " + device.getName(), Toast.LENGTH_SHORT).show();
                    if (device.getName().charAt(0) == 'j') {
                        binding.textViewStatus.setText(device.getName());
                        bluetoothDevice = device;
                        break;
                    }
                }
                BluetoothDevice[] btArray = new BluetoothDevice[1];
                if(bluetoothDevice==null){
                    Toast.makeText(getContext(), "No device found", Toast.LENGTH_SHORT).show();
                    return;
                }
                clientClass=new ClientClass(bluetoothDevice, bluetoothAdapter, getContext(), binding.textViewStatus, handler);
                clientClass.start();
                binding.textViewStatus.setText("Connecting...");
                String string= "Connect 11234";
//                clientClass.sendReceive.write(string.getBytes());


            }
        });
        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            TextView status = binding.textViewStatus;
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
                    binding.buttonConnect.setVisibility(View.GONE);
                    binding.buttonRegister.setVisibility(View.VISIBLE);
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

    public void authenticate(){
        executor = ContextCompat.getMainExecutor(getContext());
        biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authenticationMessage = "Authentication error: " + errString;
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT).show();
//                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                authenticationCompleted = true;
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authenticationMessage = "Authentication succeeded!";
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT).show();
                int studentId = ((MainActivity)getActivity()).RetrieveDataInt(0, "studentId");
                if(studentId==0){
                    Toast.makeText(getContext(), "StudentId not found", Toast.LENGTH_SHORT).show();
                    return;
                }
                String string= ""+studentId;
                clientClass.sendReceive.write(string.getBytes());
                authenticationCompleted = true;
                authenticationPassed = true;}

            @Override
            public void onAuthenticationFailed() {
                authenticationMessage = "Authentication failed";
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT).show();
//                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                authenticationCompleted = true;
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("You are about register for class, please finger the finger print sensor")
                .setSubtitle("Use your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);

    }
}