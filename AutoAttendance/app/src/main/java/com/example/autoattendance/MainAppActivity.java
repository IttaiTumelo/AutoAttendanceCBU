package com.example.autoattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.Activity;
import android.os.Bundle;

import com.example.autoattendance.databinding.ActivityMainAppBinding;

public class MainAppActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainAppBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

    }
}