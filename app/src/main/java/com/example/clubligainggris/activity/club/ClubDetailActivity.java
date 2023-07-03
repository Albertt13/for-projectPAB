package com.example.clubligainggris.activity.club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.clubligainggris.R;

public class ClubDetailActivity extends AppCompatActivity {

    private TextView tvNama, tvJulukan, tvTahun, tvSejarah, tvPemilik, tvTitle;
    private ImageView ivFoto;

    private String yNama, yJulukan, yTahun, ySejarah, yPemilik, yTitle, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvJulukan = findViewById(R.id.tv_julukan);
        tvTahun = findViewById(R.id.tv_tahun);
        tvSejarah = findViewById(R.id.tv_sejarah);
        tvPemilik = findViewById(R.id.tv_pemilik);
        tvTitle = findViewById(R.id.tv_title);
        ivFoto = findViewById(R.id.iv_foto);

        Intent ambil = getIntent();
        yNama = ambil.getStringExtra("xNama");
        yJulukan = ambil.getStringExtra("xJulukan");
        yTahun = ambil.getStringExtra("xTahun");
        ySejarah = ambil.getStringExtra("xSejarah");
        yPemilik = ambil.getStringExtra("xPemilik");
        yTitle = ambil.getStringExtra("xTitle");
        yFoto = ambil.getStringExtra("xFoto");

        tvNama.setText(yNama);
        tvJulukan.setText(yJulukan);
        tvTahun.setText(yTahun);
        tvSejarah.setText(ySejarah);
        tvPemilik.setText(yPemilik);
        tvTitle.setText(yTitle);
        Glide.with(this).load(yFoto).into(ivFoto);
        setTitle(yNama);
    }
}