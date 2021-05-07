package com.csci3397.mobileappdev.tracciesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Map;

public class ApplicationMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavController navController;
   SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationmain);

        preferences = getApplicationContext().getSharedPreferences("Preferences", 0);

        bottomNavigationView = findViewById(R.id.bottomnav);
        navController = Navigation.findNavController(this, R.id.fragmentnav);
        bottomNavigationView.setItemIconTintList(null);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

    }
}