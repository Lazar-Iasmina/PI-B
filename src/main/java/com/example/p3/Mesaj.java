package com.example.p3;

public class Mesaj {
    private int id;
    private int idZr;
    private String mesaj;
    private int ord;

    public Mesaj() {
    }

    public Mesaj(int id, int idZr, String mesaj, int ord) {
        this.id = id;
        this.idZr = idZr;
        this.mesaj = mesaj;
        this.ord = ord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdZr() {
        return idZr;
    }

    public void setIdZr(int idZr) {
        this.idZr = idZr;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    @Override
    public String toString() {
        return "Mesaj{" +
                "id=" + id +
                ", idZr=" + idZr +
                ", mesaj='" + mesaj + '\'' +
                ", ord=" + ord +
                '}';
    }
}
