package com.example.clubligainggris.api;

import com.example.clubligainggris.model.club.ClubModelResponse;
import com.example.clubligainggris.model.stadion.StadionModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {

    @GET("retrieve.php")
    Call<ClubModelResponse> ardRetrieve();

    @GET("retrieve2.php")
    Call<StadionModelResponse> ardRetrieve2();

    @FormUrlEncoded
    @POST("create.php")
    Call<ClubModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("julukan") String julukan,
            @Field("tahun_berdiri") String tahun_berdiri,
            @Field("sejarah") String sejarah,
            @Field("pemilik") String pemilik,
            @Field("title") String title,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("create2.php")
    Call<StadionModelResponse> ardCreate2(
            @Field("nama") String nama,
            @Field("lokasi") String lokasi,
            @Field("tahun_berdiri") String tahun_berdiri,
            @Field("kapasitas") String kapasitas,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ClubModelResponse> ardUpdate(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("julukan") String julukan,
            @Field("tahun_berdiri") String tahun_berdiri,
            @Field("sejarah") String sejarah,
            @Field("pemilik") String pemilik,
            @Field("title") String title,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update2.php")
    Call<StadionModelResponse> ardUpdate2(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("lokasi") String lokasi,
            @Field("tahun_berdiri") String tahun_berdiri,
            @Field("kapasitas") String kapasitas,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ClubModelResponse> ardDelete(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("delete2.php")
    Call<StadionModelResponse> ardDelete2(
            @Field("id") String id
    );
}
