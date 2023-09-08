package com.example.autoattendance;

import static com.example.autoattendance.BaseStatics.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.autoattendance.databinding.FragmentRegistrationBinding;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {

    AttendanceApi attendanceApi =  retrofit.create(AttendanceApi.class);
    ArrayList<Program> programs;
    ArrayAdapter<CharSequence> programsList;
    SharedPreferences sharedPreferences;
            KeyGenerator keyGenerator;
    KeyStore keyStore;
    SecretKey key;
    Cipher cipher;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentRegistrationBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        // binding the view with the layout file
        binding = FragmentRegistrationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        attendanceApi.getProgarms().enqueue(new Callback<List<Program>>() {
            @Override
            public void onResponse(Call<List<Program>> call, Response<List<Program>> response) {
                programs = new ArrayList<>(response.body());
                programsList = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_spinner_item);
                for (Program program : programs ) {
                    programsList.add(program.name);
                }
                binding.spProgram.setAdapter(programsList);

            }

            @Override
            public void onFailure(Call<List<Program>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to get programs", Toast.LENGTH_SHORT).show();
            }
        });

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( binding.etFirstName.getText().toString().isEmpty()) {
                    binding.etFirstName.setError("First name is required");
                    return;
                }
                if( binding.etLastName.getText().toString().isEmpty()) {
                    binding.etLastName.setError("Last name is required");
                    return;
                }
                if( binding.etPassword.getText().toString().isEmpty()) {
                    binding.etPassword.setError("Email is required");
                    return;
                }
                if( binding.etPassword2.getText().toString().isEmpty()) {
                    binding.etPassword2.setError("Password is required");
                    return;
                }
                if( binding.etStudentNumber.getText().toString().isEmpty()) {
                    binding.etStudentNumber.setError("StudentNumber is required");
                    return;
                }
                if( binding.etYear.getText().toString().isEmpty()) {
                    binding.etYear.setError("Email is required");
                    return;
                }
                if(! binding.etPassword.getText().toString().equals(binding.etPassword2.getText().toString())) {
                    binding.etPassword2.setError("Passwords do not match");
                    return;
                }
                Student student = new Student(
                        binding.etFirstName.getText().toString(),
                        binding.etLastName.getText().toString(), "",
                        binding.etStudentNumber.getText().toString(),
                        binding.etPassword.getText().toString(),
                        Integer.parseInt(binding.etYear.getText().toString()),
                        binding.spProgram.getSelectedItemPosition() + 1
                );
                attendanceApi.registerStudent(student).enqueue(new Callback<Student>() {
                    @Override
                    public void onResponse(Call<Student> call, Response<Student> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();

                            //Store respose on phone memory
                            MyDatabaseHelper myDB = new MyDatabaseHelper(getContext());
                            myDB.addStudent(response.body().StudentNumber, response.body().FirstName, response.body().id);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putBoolean("approved", false);
//                            editor.putString("studentNumber", response.body().StudentNumber);
//                            editor.putString("firstName", response.body().FirstName);
//                            editor.putString("lastName", response.body().LastName);
//                            editor.putString("email", response.body().Email);
//                            editor.putInt("year", response.body().Year);
//                            editor.putInt("programId", response.body().ProgramId);
//                            editor.putInt("id", response.body().id);
//                            editor.apply();
                            NavHostFragment.findNavController(RegistrationFragment.this)
                                    .navigate(R.id.action_registrationFragment_to_approvalFragment);
                        } else {
                            Toast.makeText(getContext(), "Registration failed becayse is" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Student> call, Throwable t) {
                        Toast.makeText(getContext(), "Registration failed - failed to post", Toast.LENGTH_SHORT).show();
                    }
                });
//                try {
//                    keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
//                    keyStore = KeyStore.getInstance("AndroidKeyStore");
//                    keyStore.load(null);
//
//                    //Initialize the key generator
//                    keyGenerator.init(new KeyGenParameterSpec.Builder(
//                            "MyKey",
//                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
//                            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
//                            .setUserAuthenticationRequired(true)
//                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
//                            .build());
//                    //Generate the key
//                    keyGenerator.generateKey();
//                    key = (SecretKey) keyStore.getKey("MyKey", null);
//                    cipher = Cipher.getInstance(
//                            KeyProperties.KEY_ALGORITHM_AES + "/"
//                                    + KeyProperties.BLOCK_MODE_CBC + "/"
//                                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
//                } catch (NoSuchAlgorithmException | NoSuchProviderException |
//                         InvalidAlgorithmParameterException | java.security.cert.CertificateException | java.io.IOException |
//                         NoSuchPaddingException e) {
//                    e.printStackTrace();
//                } catch (UnrecoverableKeyException e) {
//                    throw new RuntimeException(e);
//                } catch (KeyStoreException e) {
//                    throw new RuntimeException(e);
//                }
//                NavHostFragment.findNavController(RegistrationFragment.this)
//                        .navigate(R.id.action_registrationFragment_to_approvalFragment);
            }
        });


    }



}