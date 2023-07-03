package com.example.clubligainggris.activity.club;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.example.clubligainggris.R;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.club.ClubModelResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubTambahActivity extends AppCompatActivity {
    private EditText etNama, etJulukan, etTahun, etSejarah, etPemilik, etTitle, etFoto;
    private Button btnTambah;
    private String nama, julukan, tahun, sejarah, pemilik, title, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_tambah);

        etNama = findViewById(R.id.et_nama);
        etJulukan = findViewById(R.id.et_julukan);
        etPemilik = findViewById(R.id.et_pemilik);
        etSejarah = findViewById(R.id.et_sejarah);
        etTitle = findViewById(R.id.et_title);
        etTahun = findViewById(R.id.et_tahun);
        etFoto = findViewById(R.id.et_foto);

        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
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
                    tambahClub();
                }
            }
        });
    }

    private void tambahClub() {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ClubModelResponse> proses = ARD.ardCreate(nama, julukan, tahun, sejarah, pemilik, title, foto);

        proses.enqueue(new Callback<ClubModelResponse>() {
            @Override
            public void onResponse(Call<ClubModelResponse> call, Response<ClubModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                Toast.makeText(ClubTambahActivity.this, "Kode : " + kode + ", Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ClubModelResponse> call, Throwable t) {
                Toast.makeText(ClubTambahActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}