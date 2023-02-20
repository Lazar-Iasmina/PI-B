package com.example.p3;

public class ZborRezervat {
    private int idZr;
    private int idUser;
    private int idZbor;
    private String loc;

    public ZborRezervat() {
    }

    public ZborRezervat(int idZr, int idUser, int idZbor, String loc) {
        this.idZr = idZr;
        this.idUser = idUser;
        this.idZbor = idZbor;
        this.loc = loc;
    }

    public int getIdZr() {
        return idZr;
    }

    public void setIdZr(int idZr) {
        this.idZr = idZr;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdZbor() {
        return idZbor;
    }

    public void setIdZbor(int idZbor) {
        this.idZbor = idZbor;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return idZr +
                " " + idUser +
                " " + idZbor +
                " " + loc ;
    }
}
