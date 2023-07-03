package com.example.clubligainggris.activity.club;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.clubligainggris.R;
import com.example.clubligainggris.activity.stadion.StadionActivity;
import com.example.clubligainggris.adapter.ClubViewAdapter;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.club.ClubModelResponse;
import com.example.clubligainggris.model.club.ModelClub;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubActivity extends AppCompatActivity {

    private RecyclerView rvClub;

    private BottomNavigationView navigasibawah;
    private RecyclerView.Adapter adClub;

    private FloatingActionButton fabTambah;
    private RecyclerView.LayoutManager LMClub;

    private ProgressBar pbClub;
    private List<ModelClub> listClub = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        setTitle("English League Club");

        rvClub = findViewById(R.id.rv_club);
        fabTambah = findViewById(R.id.fab_tambah_data);
        pbClub = findViewById(R.id.pb_club);
        navigasibawah = findViewById(R.id.bnv_navigasi_bawah);

        LMClub = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvClub.setLayoutManager(LMClub);

        navigasibawah.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_club:
                        return true;
                    case R.id.menu_stadion:
                        Intent intent = new Intent(ClubActivity.this, StadionActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClubActivity.this, ClubTambahActivity.class));
            }
        });
    }

    public void retrieveClub() {
        pbClub.setVisibility(View.VISIBLE);
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ClubModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ClubModelResponse>() {
            @Override
            public void onResponse(Call<ClubModelResponse> call, Response<ClubModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listClub = response.body().getData();

                adClub = new ClubViewAdapter(ClubActivity.this, listClub);
                rvClub.setAdapter(adClub);
                adClub.notifyDataSetChanged();

                pbClub.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<ClubModelResponse> call, Throwable t) {
                Toast.makeText(ClubActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT);
                pbClub.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveClub();
    }
}