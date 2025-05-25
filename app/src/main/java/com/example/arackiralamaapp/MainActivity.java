package com.example.arackiralamaapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.arackiralamaapp.ui.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Eğer uygulama ilk defa açılıyorsa, HomeFragment'i fragment_container'a yerleştir
        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, homeFragment);
            transaction.commit();
        }
    }
}
