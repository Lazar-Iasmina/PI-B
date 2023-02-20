package com.example.p3;

public class Review {
     private int id;
     private int idDest;
     private String mesaj;
     private int nrStele;

    public Review() {
    }

    public Review(int id, int idDest, String mesaj, int nrStele) {
        this.id = id;
        this.idDest = idDest;
        this.mesaj = mesaj;
        this.nrStele = nrStele;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDest() {
        return idDest;
    }

    public void setIdDest(int idDest) {
        this.idDest = idDest;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public int getNrStele() {
        return nrStele;
    }

    public void setNrStele(int nrStele) {
        this.nrStele = nrStele;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", idDest=" + idDest +
                ", mesaj='" + mesaj + '\'' +
                ", nrStele=" + nrStele +
                '}';
    }
}
