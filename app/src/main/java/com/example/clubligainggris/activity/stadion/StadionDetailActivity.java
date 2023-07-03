package com.example.clubligainggris.activity.stadion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.clubligainggris.R;

public class StadionDetailActivity extends AppCompatActivity {

    private TextView tvNama, tvLokasi, tvTahun, tvKapasitas;
    private ImageView ivFoto;

    private String yNama, yLokasi, yTahun, yKapasitas, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadion_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvLokasi = findViewById(R.id.tv_lokasi);
        tvKapasitas = findViewById(R.id.tv_kapasitas);
        tvTahun = findViewById(R.id.tv_tahun);
        ivFoto = findViewById(R.id.iv_foto);

        Intent ambil = getIntent();
        yNama = ambil.getStringExtra("xNama");
        yLokasi = ambil.getStringExtra("xLokasi");
        yTahun = ambil.getStringExtra("xTahun");
        yKapasitas = ambil.getStringExtra("xKapasitas");
        yFoto = ambil.getStringExtra("xFoto");

        tvNama.setText(yNama);
        tvLokasi.setText(yLokasi);
        tvTahun.setText(yTahun);
        tvKapasitas.setText(yKapasitas);
        Glide.with(this).load(yFoto).into(ivFoto);
        setTitle(yNama);
    }
}