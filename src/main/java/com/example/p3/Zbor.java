package com.example.p3;

public class Zbor {
    private int id;
    private String companie;
    private String aeroportPLecare;
    private String aeroportSosire;
    private String dataPlecare;
    private String dataSosire;
    private String oraPlecare;
    private String oraSosire;
    private int nrLocuri;

    public Zbor() {
    }

    public Zbor(int id, String companie, String aeroportPLecare, String aeroportSosire, String dataPlecare, String dataSosire, String oraPlecare, String oraSosire, int nrLocuri) {
        this.id = id;
        this.companie = companie;
        this.aeroportPLecare = aeroportPLecare;
        this.aeroportSosire = aeroportSosire;
        this.dataPlecare = dataPlecare;
        this.dataSosire = dataSosire;
        this.oraPlecare = oraPlecare;
        this.oraSosire = oraSosire;
        this.nrLocuri = nrLocuri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanie() {
        return companie;
    }

    public void setCompanie(String companie) {
        this.companie = companie;
    }

    public String getAeroportPLecare() {
        return aeroportPLecare;
    }

    public void setAeroportPLecare(String aeroportPLecare) {
        this.aeroportPLecare = aeroportPLecare;
    }

    public String getAeroportSosire() {
        return aeroportSosire;
    }

    public void setAeroportSosire(String aeroportSosire) {
        this.aeroportSosire = aeroportSosire;
    }

    public String getDataPlecare() {
        return dataPlecare;
    }

    public void setDataPlecare(String dataPlecare) {
        this.dataPlecare = dataPlecare;
    }

    public String getDataSosire() {
        return dataSosire;
    }

    public void setDataSosire(String dataSosire) {
        this.dataSosire = dataSosire;
    }

    public String getOraPlecare() {
        return oraPlecare;
    }

    public void setOraPlecare(String oraPlecare) {
        this.oraPlecare = oraPlecare;
    }

    public String getOraSosire() {
        return oraSosire;
    }

    public void setOraSosire(String oraSosire) {
        this.oraSosire = oraSosire;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return id +" " + companie + " " +  aeroportPLecare + " " +  aeroportSosire+ " " + dataPlecare+ " " + dataSosire+ " " + oraPlecare +" " +  oraSosire + " " +  nrLocuri ;
    }
}
