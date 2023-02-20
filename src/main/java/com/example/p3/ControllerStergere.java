package com.example.p3;

import com.example.p3.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ControllerStergere {
    private Stage stage;
    private Scene scene;
    private Parent root;
    List<Zbor> l1 = new ArrayList<>();
    String ZborSelectat;
    @FXML
    Label zbor;
    @FXML
    ListView<String> zboruri;

    public void displayZboruri() {
        try {

            l1 = citireZbor();
            List l2 = new ArrayList();
            for (Zbor i : l1) {
                l2.add(i.toString());
            }


            String uname1, pass1;
            uname1 = "user";
            pass1 = "1234";
            /*String[] splited = x.split("\\s+");
            //aPlecare.setText(aPlecare.getText()+splited[0]);
            user=splited[0];
            zbor=splited[1]+" "+splited[2]+" "+splited[3]+" "+splited[4];*/


            System.out.println("//////*" + l2);
            zboruri.getItems().addAll(l2);
            zboruri.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    ZborSelectat = zboruri.getSelectionModel().getSelectedItem();
                    zbor.setText(ZborSelectat);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(ActionEvent e) throws IOException {

        l1=citireZbor();
        List l2 = new ArrayList();
        String x;
        x = zbor.getText();
        int ok = -1, in = 0;
        Zbor y = new Zbor();
        List<Zbor> l3 = new ArrayList<>();
        l3.clear();
        for (Zbor i : l1) {
            System.out.println(x + "    " + i.toString());
            if (i.toString().equals(x)) {
                in++;
                System.out.println("*" + x + "    " + i.toString());
            } else {
                l3.add(i);
            }

        }
        for (Zbor i : l3) {
            System.out.println(i.toString());
        }


       updateZbor(l3);
        root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void switchtoAdmin1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("meniuPrincipal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public static List<User> citireUseri() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<User> l = new ArrayList<>();
        User x = new User();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from user";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (User) createBeanuseri(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (User i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateUseri(List<User> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from user";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from user";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (User i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO user (id,email,nume,prenume,pass) " + "VALUES ( " + i.getId() + ",'" + i.getEmail() + "','" + i.getNume() + "'+'" + i.getPrenume() + "','" + i.getPass() + "')");
                st.executeUpdate("INSERT INTO user (id,email,nume,prenume,pass) " + "VALUES ( " + i.getId() + ",'" + i.getEmail() + "','" + i.getNume() + "','" + i.getPrenume() + "','" + i.getPass() + "')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanuseri(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("email"), rs.getString("nume"), rs.getString("prenume"), rs.getString("pass"));
    }

    public static List<Zbor> citireZbor() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Zbor> l = new ArrayList<>();
        Zbor x = new Zbor();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from zbor";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (Zbor) createBeanZbor(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (Zbor i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateZbor(List<Zbor> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from user";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from zbor";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (Zbor i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO user (id,companie,aeroportPlecare,aeroportSosire,dataPlecare,dataSosire,oraPlecare,oraSosire,nrLocuri) " + "VALUES ( " + i.getId() + ",'" + i.getCompanie() + "','" + i.getAeroportPLecare() + i.getAeroportSosire() + "','" + i.getDataPlecare() + "','" + i.getDataSosire() + "','" + i.getOraPlecare() + "','" + i.getOraSosire() + "'," + i.getNrLocuri() + ")");
                st.executeUpdate("INSERT INTO zbor (id,companie,aeroportPlecare,aeroportSosire,dataPlecare,dataSosire,oraPlecare,oraSosire,nrLocuri) " + "VALUES ( " + i.getId() + ",'" + i.getCompanie() + "','" + i.getAeroportPLecare() + "','" + i.getAeroportSosire() + "','" + i.getDataPlecare() + "','" + i.getDataSosire() + "','" + i.getOraPlecare() + "','" + i.getOraSosire() + "'," + i.getNrLocuri() + ")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanZbor(ResultSet rs) throws SQLException {
        return new Zbor(rs.getInt("id"), rs.getString("companie"), rs.getString("aeroportPlecare"), rs.getString("aeroportSosire"), rs.getString("dataPlecare"), rs.getString("dataSosire"), rs.getString("oraPlecare"), rs.getString("oraSosire"), rs.getInt("nrLocuri"));
    }

    public static List<ZborRezervat> citireZboruriRezervate() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<ZborRezervat> l = new ArrayList<>();
        ZborRezervat x = new ZborRezervat();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from zborurirezervate";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (ZborRezervat) createBeanZboruriRezervate(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (ZborRezervat i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateZboruriRezervate(List<ZborRezervat> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from zborurirezervate";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from zborurirezervate";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (ZborRezervat i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO zborurirezervate (idZr,idUser,idZbor,loc) " + "VALUES ( " + i.getIdZr() + "," + i.getIdUser() + "," + i.getIdZbor() + ",'" + i.getLoc() + "')");
                st.executeUpdate("INSERT INTO zborurirezervate (idZr,idUser,idZbor,loc) " + "VALUES ( " + i.getIdZr() + "," + i.getIdUser() + "," + i.getIdZbor() + ",'" + i.getLoc() + "')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanZboruriRezervate(ResultSet rs) throws SQLException {
        return new ZborRezervat(rs.getInt("idZr"), rs.getInt("idUser"), rs.getInt("idZbor"), rs.getString("loc"));
    }

    public static List<Destinatie> citireDestinatii() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Destinatie> l = new ArrayList<>();
        Destinatie x = new Destinatie();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from destinatii";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (Destinatie) createBeanDestinatii(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (Destinatie i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateDestinatii(List<Destinatie> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from destinatii";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from destinatii";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (Destinatie i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO destinatii (id,nume,despre) " + "VALUES ( " + i.getId() + ",'" + i.getNume() + "','" + i.getDespre() + "')");
                st.executeUpdate("INSERT INTO destinatii (id,nume,despre) " + "VALUES ( " + i.getId() + ",'" + i.getNume() + "','" + i.getDespre() + "')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanDestinatii(ResultSet rs) throws SQLException {
        return new Destinatie(rs.getInt("id"), rs.getString("nume"), rs.getString("despre"));
    }

    public static List<Mesaj> citireMesaje() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Mesaj> l = new ArrayList<>();
        Mesaj x = new Mesaj();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from mesaje";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (Mesaj) createBeanMesaje(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (Mesaj i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateMesaje(List<Mesaj> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from mesaje";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from mesaje";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (Mesaj i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO mesaje (id,idZr,mesaj,ord) " + "VALUES ( " + i.getId() + "," + i.getIdZr() + ",'" + i.getMesaj() + "'," + i.getOrd() + ")");
                st.executeUpdate("INSERT INTO mesaje (id,idZr,mesaj,ord) " + "VALUES ( " + i.getId() + "," + i.getIdZr() + ",'" + i.getMesaj() + "'," + i.getOrd() + ")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanMesaje(ResultSet rs) throws SQLException {
        return new Mesaj(rs.getInt("id"), rs.getInt("idZr"), rs.getString("mesaj"), rs.getInt("ord"));
    }

    public static List<Review> citireReviewuri() {
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Review> l = new ArrayList<>();
        Review x = new Review();
        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        var sql = "select * from reviewuri";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                x = (Review) createBeanReviewuri(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for (Review i : l) {
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return l;

    }

    public static void updateReviewuri(List<Review> l) {
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url = "jdbc:mysql://localhost:3306/p3";
        var user = "root";
        var password = "";
        String sql = "select * from reviewuri";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql = "delete from reviewuri";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for (Review i : l) {
                System.out.println(i);
                System.out.println("INSERT INTO mesaje (id,idDest,mesaj,nrStele) " + "VALUES ( " + i.getId() + "," + i.getIdDest() + ",'" + i.getMesaj() + "'," + i.getNrStele() + ")");
                st.executeUpdate("INSERT INTO reviewuri (id,idDest,mesaj,nrStele) " + "VALUES ( " + i.getId() + "," + i.getIdDest() + ",'" + i.getMesaj() + "'," + i.getNrStele() + ")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Object createBeanReviewuri(ResultSet rs) throws SQLException {
        return new Review(rs.getInt("id"), rs.getInt("idDest"), rs.getString("mesaj"), rs.getInt("nrStele"));
    }
}
