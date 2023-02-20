package com.example.p3;

public class User {
    private int id;
    private String email;
    private String nume;
    private String prenume;
    private String pass;

    public User(int id, String email, String nume, String prenume, String pass) {
        this.id = id;
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.pass = pass;
    }

    public User(String email, String nume, String prenume, String pass) {
        this.email = email;
        this.nume = nume;
        this.prenume = prenume;
        this.pass = pass;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
