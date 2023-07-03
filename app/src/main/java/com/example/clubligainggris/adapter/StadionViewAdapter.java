package com.example.clubligainggris.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.clubligainggris.R;
import com.example.clubligainggris.activity.stadion.StadionActivity;
import com.example.clubligainggris.activity.stadion.StadionDetailActivity;
import com.example.clubligainggris.activity.stadion.StadionUbahActivity;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.stadion.ModelStadion;
import com.example.clubligainggris.model.stadion.StadionModelResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StadionViewAdapter extends RecyclerView.Adapter<StadionViewAdapter.VHStadion> {

    private Context ctx;
    private List<ModelStadion> listStadion;

    public StadionViewAdapter(Context ctx, List<ModelStadion> listStadion) {
        this.ctx = ctx;
        this.listStadion = listStadion;
    }

    @NonNull
    @Override
    public StadionViewAdapter.VHStadion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_stadion, parent, false);
        return new StadionViewAdapter.VHStadion(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull StadionViewAdapter.VHStadion holder, int position) {
        ModelStadion MS = listStadion.get(position);
        holder.tvId.setText(MS.getId());
        holder.tvNama.setText(MS.getNama());
        holder.tvLokasi.setText(MS.getLokasi());
        holder.tvTahun.setText(MS.getTahun_berdiri());
        holder.tvKapasitas.setText(MS.getKapasitas());
        holder.tvFoto.setText(MS.getFoto());
        Glide.with(holder.itemView.getContext()).load(MS.getFoto()).
                apply(new RequestOptions().override(750, 750)).
                into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return listStadion.size();
    }

    public class VHStadion extends RecyclerView.ViewHolder {

        TextView tvId, tvNama, tvLokasi, tvTahun, tvKapasitas, tvFoto;

        ImageView ivFoto;

        public VHStadion(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvLokasi = itemView.findViewById(R.id.tv_lokasi);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            tvKapasitas = itemView.findViewById(R.id.tv_kapasitas);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            ivFoto = itemView.findViewById(R.id.iv_foto);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Perhatian");
                    pesan.setMessage("Operasi apa yang akan dilakukan?");
                    pesan.setCancelable(true);

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent pindah = new Intent(ctx, StadionUbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xLokasi", tvLokasi.getText().toString());
                            pindah.putExtra("xTahun", tvTahun.getText().toString());
                            pindah.putExtra("xKapasitas", tvKapasitas.getText().toString());
                            pindah.putExtra("xFoto", tvFoto.getText().toString());

                            ctx.startActivity(pindah);
                        }
                    });
                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusStadion(tvId.getText().toString());
                        }
                    });

                    pesan.show();
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, StadionDetailActivity.class);
                    pindah.putExtra("xNama", tvNama.getText().toString());
                    pindah.putExtra("xLokasi", tvLokasi.getText().toString());
                    pindah.putExtra("xTahun", tvTahun.getText().toString());
                    pindah.putExtra("xKapasitas", tvKapasitas.getText().toString());
                    pindah.putExtra("xFoto", tvFoto.getText().toString());
                    ctx.startActivity(pindah);
                }
            });
        }

        public void hapusStadion(String idStadion) {
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<StadionModelResponse> proses = ARD.ardDelete2(idStadion);

            proses.enqueue(new Callback<StadionModelResponse>() {
                @Override
                public void onResponse(Call<StadionModelResponse> call, Response<StadionModelResponse> response) {
                    String pesan = response.body().getPesan();
                    String kode = response.body().getKode();
                    Toast.makeText(ctx, "Kode = " + kode + ", Pesan :  " + pesan, Toast.LENGTH_SHORT).show();
                    ((StadionActivity) ctx).retrieveStadion();
                }

                @Override
                public void onFailure(Call<StadionModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
