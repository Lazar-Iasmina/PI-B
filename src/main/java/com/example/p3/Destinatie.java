package com.example.p3;

public class Destinatie {
    private int id;
    private String nume;
    private String despre;

    public Destinatie() {
    }

    public Destinatie(int id, String nume, String despre) {
        this.id = id;
        this.nume = nume;
        this.despre = despre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getDespre() {
        return despre;
    }

    public void setDespre(String despre) {
        this.despre = despre;
    }

    @Override
    public String toString() {
        return "Destinatie{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", despre='" + despre + '\'' +
                '}';
    }
}
