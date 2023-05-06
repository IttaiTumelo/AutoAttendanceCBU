package com.example.autoattendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.autoattendance.databinding.FragmentLoginBinding;

import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentLoginBinding binding;
    SharedPreferences sharedPreferences;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    private boolean authenticationPassed = false;
    private boolean authenticationCompleted = false;
    private String authenticationMessage = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        //bind the layout to the fragment]
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        executor = ContextCompat.getMainExecutor(getContext());
        biometricPrompt = new BiometricPrompt(getActivity(), executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authenticationMessage = "Authentication error: " + errString;
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT)
                        .show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                authenticationCompleted = true;
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authenticationMessage = "Authentication succeeded!";
                Toast.makeText(getContext(), authenticationMessage, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_approvalFragment);
                authenticationCompleted = true;
                authenticationPassed = true;}

            @Override
            public void onAuthenticationFailed() {
                authenticationMessage = "Authentication failed";
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), authenticationMessage,
                                Toast.LENGTH_SHORT)
                        .show();
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
                authenticationCompleted = true;
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();


        binding.buttonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registrationFragment);
            }
        });

        //Todo : If the user has already registered, then show the biomettric data
        //Check if the user just intalled the app, by checking is the value in shared preferences is null
        sharedPreferences = requireActivity().getSharedPreferences("com.example.autoattendance", getContext().MODE_PRIVATE);
        String isUserRegistered = sharedPreferences.getString("isUserRegistered", null);
        if(isUserRegistered != null) {
            //User is already registered
            //Show the biometric prompt
            biometricPrompt.authenticate(promptInfo);
            if(authenticationCompleted) {
                if(authenticationPassed) {
                    //It auto starts the main app activity
                }
                else {
//                        startActivity(new Intent(getActivity(), MainAppActivity.class));
                }
            }
        }

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_approvalFragment);
            }
        });
    }
}