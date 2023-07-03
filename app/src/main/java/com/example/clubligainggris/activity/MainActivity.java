package com.example.clubligainggris.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.clubligainggris.R;
import com.example.clubligainggris.activity.club.ClubActivity;
import com.example.clubligainggris.activity.stadion.StadionActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnExit, btnTentang, btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMulai = findViewById(R.id.btn_start);
        btnExit = findViewById(R.id.btn_exit);
        btnTentang = findViewById(R.id.btn_about);

        btnMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, ClubActivity.class);
                startActivity(start);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Konfirmasi");
                dialog.setMessage("Keluar dari aplikasi ?");

                dialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        finish();
                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }

                });
                dialog.show();
            }
        });

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about);
            }
        });
    }
}