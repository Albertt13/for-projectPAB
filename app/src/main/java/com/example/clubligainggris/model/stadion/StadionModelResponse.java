package com.example.clubligainggris.model.stadion;

import java.util.List;

public class StadionModelResponse {

    public String kode, pesan;
    public List<ModelStadion> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelStadion> getData() {
        return data;
    }
}
