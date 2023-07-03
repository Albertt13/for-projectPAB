package com.example.clubligainggris.activity.club;

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
import com.example.clubligainggris.model.club.ClubModelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubUbahActivity extends AppCompatActivity {

    private String nama, julukan, tahun, sejarah, pemilik, title, foto;

    private EditText etNama, etJulukan, etTahun, etSejarah, etPemilik, etTitle, etFoto;
    private Button btnUbah;
    private ImageView ivFoto;
    private String yId, yNama, yJulukan, yTahun, ySejarah, yPemilik, yTitle, yFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_ubah);

        Intent ambil = getIntent();
        yId = ambil.getStringExtra("xId");
        yNama = ambil.getStringExtra("xNama");
        yJulukan = ambil.getStringExtra("xJulukan");
        yTahun = ambil.getStringExtra("xTahun");
        ySejarah = ambil.getStringExtra("xSejarah");
        yPemilik = ambil.getStringExtra("xPemilik");
        yTitle = ambil.getStringExtra("xTitle");
        yFoto = ambil.getStringExtra("xFoto");

        btnUbah = findViewById(R.id.btn_ubah);
        etNama = findViewById(R.id.et_nama);
        etJulukan = findViewById(R.id.et_julukan);
        etTahun = findViewById(R.id.et_tahun);
        etTitle = findViewById(R.id.et_title);
        etSejarah = findViewById(R.id.et_sejarah);
        etPemilik = findViewById(R.id.et_pemilik);
        etFoto = findViewById(R.id.et_foto);
        ivFoto = findViewById(R.id.iv_foto);

        etNama.setText(yNama);
        etJulukan.setText(yJulukan);
        etTahun.setText(yTahun);
        etSejarah.setText(ySejarah);
        etPemilik.setText(yPemilik);
        etTitle.setText(yTitle);
        etFoto.setText(yFoto);
        Glide.with(ClubUbahActivity.this).load(yFoto).into(ivFoto);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                julukan = etJulukan.getText().toString();
                tahun = etTahun.getText().toString();
                sejarah = etSejarah.getText().toString();
                pemilik = etPemilik.getText().toString();
                title = etTitle.getText().toString();
                foto = etFoto.getText().toString();

                if (nama.trim().isEmpty()) {
                    etNama.setError("Nama Club Harus di Isi!!!");
                } else if (julukan.trim().isEmpty()) {
                    etJulukan.setError("Nama Julukan Harus di Isi!!!");
                } else if (tahun.trim().isEmpty()) {
                    etTahun.setError("Tahun Berdiri Harus di Isi!!!");
                } else if (sejarah.trim().isEmpty()) {
                    etSejarah.setError("Sejarah Club Harus di Isi!!!");
                } else if (pemilik.trim().isEmpty()) {
                    etPemilik.setError("Nama Pemilik Club Harus di Isi!!!");
                } else if (title.trim().isEmpty()) {
                    etTitle.setError("Title Harus di Isi!!!");
                } else if (foto.trim().isEmpty()) {
                    etFoto.setError("Link Foto Harus di Isi!!!");
                } else {
                    ubahClub();
                }
            }
        });
    }

    private void ubahClub() {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ClubModelResponse> proses = ARD.ardUpdate(yId, nama, julukan, tahun, sejarah, pemilik, title, foto);

        proses.enqueue(new Callback<ClubModelResponse>() {
            @Override
            public void onResponse(Call<ClubModelResponse> call, Response<ClubModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ClubUbahActivity.this, "Kode : " + kode + ", Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ClubModelResponse> call, Throwable t) {
                Toast.makeText(ClubUbahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
