package com.example.clubligainggris.activity.stadion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.clubligainggris.R;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.stadion.StadionModelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StadionUbahActivity extends AppCompatActivity {

    private String nama, lokasi, tahun, kapasitas, foto;

    private EditText etNama, etLokasi, etTahun, etKapasitas, etFoto;
    private Button btnUbah;

    private ImageView ivFoto;
    private String yId, yNama, yLokasi, yTahun, yKapasitas, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadion_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yLokasi = ambil.getStringExtra("xLokasi");
        yTahun = ambil.getStringExtra("xTahun");
        yKapasitas = ambil.getStringExtra("xKapasitas");
        yFoto = ambil.getStringExtra("xFoto");

        btnUbah = findViewById(R.id.btn_ubah);
        etNama = findViewById(R.id.et_nama);
        etLokasi = findViewById(R.id.et_lokasi);
        etTahun = findViewById(R.id.et_tahun);
        etKapasitas = findViewById(R.id.et_kapasitas);
        etFoto = findViewById(R.id.et_foto);
        ivFoto = findViewById(R.id.iv_foto);

        etNama.setText(yNama);
        etLokasi.setText(yLokasi);
        etTahun.setText(yTahun);
        etKapasitas.setText(yKapasitas);
        etFoto.setText(yFoto);
        Glide.with(this).load(yFoto).into(ivFoto);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                lokasi = etLokasi.getText().toString();
                tahun = etTahun.getText().toString();
                kapasitas = etKapasitas.getText().toString();
                foto = etFoto.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama Stadion Harus di Isi!!!");
                } else if (lokasi.trim().isEmpty()) {
                    etLokasi.setError("Lokasi Stadion Harus di Isi!!!");
                } else if (tahun.trim().isEmpty()) {
                    etTahun.setError("Tahun Berdiri Harus di Isi!!!");
                } else if (kapasitas.trim().isEmpty()) {
                    etKapasitas.setError("Jumlah Kapasitas Kursi Harus di Isi!!!");
                } else if (foto.trim().isEmpty()) {
                    etFoto.setError("Link Foto Harus di Isi!!!");
                } else {
                    ubahStadion();
                }
            }
        });
    }

    private void ubahStadion() {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<StadionModelResponse> proses = ARD.ardUpdate2(yId, nama, lokasi, tahun, kapasitas, foto);

        proses.enqueue(new Callback<StadionModelResponse>() {
            @Override
            public void onResponse(Call<StadionModelResponse> call, Response<StadionModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(StadionUbahActivity.this, "Kode : " + kode + ", Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<StadionModelResponse> call, Throwable t) {
                Toast.makeText(StadionUbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
