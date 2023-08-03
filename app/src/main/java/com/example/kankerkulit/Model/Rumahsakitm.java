package com.example.kankerkulit.Model;

public class Rumahsakitm {
    private String labeltempat;
    private String alamat;
    private String namatempat;
    private String pictempat;

    public String nomer;


    public Rumahsakitm(String labeltempat, String namatempat, String pictempat, String alamat, String nomer ) {
        this.labeltempat = labeltempat;
        this.namatempat = namatempat;
        this.pictempat = pictempat;
        this.alamat = alamat;
        this.nomer = nomer;
    }
    public String getLabeltempat() {
        return labeltempat;
    }

    public void setLabeltempat(String labeltempat) {
        this.labeltempat = labeltempat;
    }

    public String getNamatempat() {
        return namatempat;
    }

    public void setNamatempat(String namatempat) {
        this.namatempat = namatempat;
    }

    public String getPictempat() {
        return pictempat;
    }

    public void setPictempat(String pictempat) {
        this.pictempat = pictempat;
    }

    public  String getAlamat(){ return  alamat;}

    public  void setAlamat(String alamat) {this.alamat = alamat;}

    public  String getNomer(){ return  nomer;}

    public  void setNomer(String nomer) {this.nomer = nomer;}

    public Rumahsakitm()
    {

    }
}
