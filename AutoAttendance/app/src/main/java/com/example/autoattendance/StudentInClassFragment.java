package com.example.autoattendance;

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

import android.os.Handler;
import android.os.Message;
import android.security.keystore.KeyProperties;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autoattendance.databinding.FragmentStudentInClassBinding;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StudentInClassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentInClassFragment extends Fragment {
    View view;

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





        binding.buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textViewStatus.setText("Checking your bio-metrics...");

                //        Create a key Generator
                try{
                    KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
                    keyStore.load(null);
                    SecretKey key = (SecretKey) keyStore.getKey("MyKey", null);
                    Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" +
                            KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                    cipher.init(Cipher.ENCRYPT_MODE, key);
//        } catch (KeyPermanentlyInvalidatedException e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (InvalidKeyException | KeyStoreException | UnrecoverableKeyException |
                         NoSuchPaddingException | CertificateException | IOException |
                         NoSuchAlgorithmException e){
//                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.textViewStatus.setText("You have changed your bio-metrics. Please re-register with the school admin. Your issue has been reported to your school admin.");
                    binding.buttonRegister.setVisibility(View.GONE);
                    binding.buttonConnect.setVisibility(View.GONE);
                    return;
                }


                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice bluetoothDevice = null;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                for (BluetoothDevice device : bluetoothAdapter.getBondedDevices()) {
                    if (device.getName().charAt(0) == 'L') {
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
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT)
                        .show();
//                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                authenticationCompleted = true;
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authenticationMessage = "Authentication succeeded!";
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT).show();
                String string= "Test message";
                clientClass.sendReceive.write(string.getBytes());
                authenticationCompleted = true;
                authenticationPassed = true;}

            @Override
            public void onAuthenticationFailed() {
                authenticationMessage = "Authentication failed";
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), authenticationMessage,
                                Toast.LENGTH_SHORT)
                        .show();
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