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
import com.example.clubligainggris.activity.club.ClubActivity;
import com.example.clubligainggris.activity.club.ClubDetailActivity;
import com.example.clubligainggris.activity.club.ClubUbahActivity;
import com.example.clubligainggris.api.APIRequestData;
import com.example.clubligainggris.api.RetroServer;
import com.example.clubligainggris.model.club.ClubModelResponse;
import com.example.clubligainggris.model.club.ModelClub;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubViewAdapter extends RecyclerView.Adapter<ClubViewAdapter.VHClub> {

    private Context ctx;
    private List<ModelClub> listClub;

    public ClubViewAdapter(Context ctx, List<ModelClub> listClub) {
        this.ctx = ctx;
        this.listClub = listClub;
    }

    @NonNull
    @Override
    public ClubViewAdapter.VHClub onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_club, parent, false);
        return new ClubViewAdapter.VHClub(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHClub holder, int position) {
        ModelClub MC = listClub.get(position);
        holder.tvId.setText(MC.getId());
        holder.tvNama.setText(MC.getNama());
        holder.tvJulukan.setText(MC.getJulukan());
        holder.tvTahun.setText(MC.getTahun_berdiri());
        holder.tvSejarah.setText(MC.getSejarah());
        holder.tvPemilik.setText(MC.getPemilik());
        holder.tvTitle.setText(MC.getTitle());
        holder.tvFoto.setText(MC.getFoto());
        Glide.with(holder.itemView.getContext()).load(MC.getFoto()).
                apply(new RequestOptions().override(750, 750)).
                into(holder.ivFoto);

    }

    @Override
    public int getItemCount() {
        return listClub.size();
    }

    public class VHClub extends RecyclerView.ViewHolder {

        TextView tvId, tvNama, tvJulukan, tvTahun, tvSejarah, tvPemilik, tvTitle, tvFoto;

        ImageView ivFoto;

        public VHClub(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvJulukan = itemView.findViewById(R.id.tv_julukan);
            tvSejarah = itemView.findViewById(R.id.tv_sejarah);
            tvTahun = itemView.findViewById(R.id.tv_tahun);
            tvPemilik = itemView.findViewById(R.id.tv_pemilik);
            tvTitle = itemView.findViewById(R.id.tv_title);
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
                            Intent pindah = new Intent(ctx, ClubUbahActivity.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xNama", tvNama.getText().toString());
                            pindah.putExtra("xJulukan", tvJulukan.getText().toString());
                            pindah.putExtra("xTahun", tvTahun.getText().toString());
                            pindah.putExtra("xSejarah", tvSejarah.getText().toString());
                            pindah.putExtra("xPemilik", tvPemilik.getText().toString());
                            pindah.putExtra("xTitle", tvTitle.getText().toString());
                            pindah.putExtra("xFoto", tvFoto.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });
                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hapusClub(tvId.getText().toString());
                        }
                    });

                    pesan.show();
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah = new Intent(ctx, ClubDetailActivity.class);
                    pindah.putExtra("xNama", tvNama.getText().toString());
                    pindah.putExtra("xJulukan", tvJulukan.getText().toString());
                    pindah.putExtra("xTahun", tvTahun.getText().toString());
                    pindah.putExtra("xSejarah", tvSejarah.getText().toString());
                    pindah.putExtra("xPemilik", tvPemilik.getText().toString());
                    pindah.putExtra("xTitle", tvTitle.getText().toString());
                    pindah.putExtra("xFoto", tvFoto.getText().toString());
                    ctx.startActivity(pindah);
                }
            });
        }

        public void hapusClub(String idClub) {
            APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ClubModelResponse> proses = ARD.ardDelete(idClub);

            proses.enqueue(new Callback<ClubModelResponse>() {
                @Override
                public void onResponse(Call<ClubModelResponse> call, Response<ClubModelResponse> response) {
                    String pesan = response.body().getPesan();
                    String kode = response.body().getKode();
                    Toast.makeText(ctx, "Kode = " + kode + ", Pesan :  " + pesan, Toast.LENGTH_SHORT).show();
                    ((ClubActivity) ctx).retrieveClub();
                }

                @Override
                public void onFailure(Call<ClubModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
