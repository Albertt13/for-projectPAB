package com.example.clubligainggris.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.clubligainggris.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("Tentang Saya");
    }
}