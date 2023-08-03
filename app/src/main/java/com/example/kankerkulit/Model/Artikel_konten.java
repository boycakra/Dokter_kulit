package com.example.kankerkulit.Model;

public class Artikel_konten {
    private String judul;

    private String isikonten;
    private String fotokonten;



    public Artikel_konten(String judul, String isikonten, String fotokonten) {
        this.judul = judul;
        this.isikonten = isikonten;
        this.fotokonten = fotokonten;

    }
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsikonten() {
        return isikonten;
    }

    public void setIsikonten(String isikonten) {
        this.isikonten = isikonten;
    }

    public String getFotokonten() {
        return fotokonten;
    }

    public void setFotokonten(String fotokonten) {
        this.fotokonten = fotokonten;
    }


    public Artikel_konten()
    {

    }
}