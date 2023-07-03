package com.example.clubligainggris.model.club;

import java.util.List;

public class ClubModelResponse {

    public String kode, pesan;
    public List<ModelClub> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelClub> getData() {
        return data;
    }
}
