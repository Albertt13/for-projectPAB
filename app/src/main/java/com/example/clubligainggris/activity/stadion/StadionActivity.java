package com.example.clubligainggris.activity.stadion;

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
import com.example.clubligainggris.activity.club.ClubActivity;
import com.example.clubligainggris.adapter.StadionViewAdapter;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.stadion.ModelStadion;
import com.example.clubligainggris.model.stadion.StadionModelResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StadionActivity extends AppCompatActivity {

    private RecyclerView rvStadion;

    private BottomNavigationView navigasibawah;
    private RecyclerView.Adapter adStadion;

    private FloatingActionButton fabTambah;
    private RecyclerView.LayoutManager LMStadion;

    private ProgressBar pbStadion;
    private List<ModelStadion> listStadion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadion);

        setTitle("English League Stadion");

        rvStadion = findViewById(R.id.rv_stadion);
        fabTambah = findViewById(R.id.fab_tambah_data);
        pbStadion = findViewById(R.id.pb_stadion);
        navigasibawah = findViewById(R.id.bnv_navigasi_bawah);

        LMStadion = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvStadion.setLayoutManager(LMStadion);

        navigasibawah.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_stadion:
                        return true;
                    case R.id.menu_club:
                        Intent intent = new Intent(StadionActivity.this, ClubActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StadionActivity.this, StadionTambahActivity.class));
            }
        });
    }

    public void retrieveStadion() {
        pbStadion.setVisibility(View.VISIBLE);
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<StadionModelResponse> proses = ARD.ardRetrieve2();

        proses.enqueue(new Callback<StadionModelResponse>() {
            @Override
            public void onResponse(Call<StadionModelResponse> call, Response<StadionModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listStadion = response.body().getData();

                adStadion = new StadionViewAdapter(StadionActivity.this, listStadion);
                rvStadion.setAdapter(adStadion);
                adStadion.notifyDataSetChanged();

                pbStadion.setVisibility(View.GONE);
            }


            @Override
            public void onFailure(Call<StadionModelResponse> call, Throwable t) {
                Toast.makeText(StadionActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT);
                pbStadion.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveStadion();
    }
}