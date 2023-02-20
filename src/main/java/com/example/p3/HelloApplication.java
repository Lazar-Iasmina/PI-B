package com.example.p3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//vlgnalaoreclnabl
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * Aceasta clasa are rolul de a controla partea de login/ sign in si primele scene din partea de user
 *
 * @author  Iasmina Lazar
 * @version 1.0
 * @since   2022-11-29
 */
public class HelloApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }
    public void start(Stage primaryStage) throws Exception{
        /**
         * Acasta metoda lanseaza aplixcatia si da posibilitatea userului
         * sa acceseze meniul de login
         */
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene=new Scene(root);

        List<User> u=new ArrayList<>();
        u=citireUseri();
       // User uz=new User(13,"qfafq","qg","qfw","qf");
        //u.add(uz);
        updateUseri(u);
        primaryStage.setScene(scene);
        primaryStage.show();
        List<Zbor> z=new ArrayList<>();
        Zbor zb=new Zbor(32,"av","awf","awf","awf","awf","awf","awf",5);
        z=citireZbor();
        //z.add(zb);
        updateZbor(z) ;
        List<ZborRezervat> zr=new ArrayList<>();
      //  ZborRezervat zrr=new ZborRezervat(5,2,3,"a1");
        zr=citireZboruriRezervate();
      // zr.add(zrr);
        updateZboruriRezervate(zr);
        List<Destinatie> dst=new ArrayList<>();
        Destinatie d=new Destinatie(23,"134","geww");
        dst=citireDestinatii();
        //dst.add(d);
        updateDestinatii(dst);
        List<Mesaj> msg=new ArrayList<>();
        Mesaj m=new Mesaj(25,45,"afeqe",4);
        msg=citireMesaje();
       // msg.add(m);
        updateMesaje(msg);
        List<Review> rev=new ArrayList<>();
        Review re=new Review(3,4,"fwq",4);
        rev=citireReviewuri();
        //rev.add(re);
        updateReviewuri(rev);

    }



    public static List<User> citireUseri(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<User> l= new ArrayList<>();
        User x= new User();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from user";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (User) createBeanuseri(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(User i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateUseri(List<User> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from user";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from user";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(User i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO user (id,email,nume,prenume,pass) " + "VALUES ( "+i.getId()+",'"+i.getEmail()+"','"+i.getNume()+"'+'"+i.getPrenume()+"','"+i.getPass()+"')");
                st.executeUpdate("INSERT INTO user (id,email,nume,prenume,pass) " + "VALUES ( "+i.getId()+",'"+i.getEmail()+"','"+i.getNume()+"','"+i.getPrenume()+"','"+i.getPass()+"')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanuseri( ResultSet rs ) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("email"),rs.getString("nume"),rs.getString("prenume"),rs.getString("pass"));
    }
    public static List<Zbor> citireZbor(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Zbor> l= new ArrayList<>();
        Zbor x= new Zbor();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from zbor";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (Zbor) createBeanZbor(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(Zbor i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateZbor(List<Zbor> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from user";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from zbor";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(Zbor i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO user (id,companie,aeroportPlecare,aeroportSosire,dataPlecare,dataSosire,oraPlecare,oraSosire,nrLocuri) " + "VALUES ( "+i.getId()+",'"+i.getCompanie()+"','"+i.getAeroportPLecare()+i.getAeroportSosire()+"','"+i.getDataPlecare()+"','"+i.getDataSosire()+"','"+i.getOraPlecare()+"','"+i.getOraSosire()+"',"+i.getNrLocuri()+")");
                st.executeUpdate("INSERT INTO zbor (id,companie,aeroportPlecare,aeroportSosire,dataPlecare,dataSosire,oraPlecare,oraSosire,nrLocuri) " + "VALUES ( "+i.getId()+",'"+i.getCompanie()+"','"+i.getAeroportPLecare()+"','"+i.getAeroportSosire()+"','"+i.getDataPlecare()+"','"+i.getDataSosire()+"','"+i.getOraPlecare()+"','"+i.getOraSosire()+"',"+i.getNrLocuri()+")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanZbor( ResultSet rs ) throws SQLException {
        return new Zbor(rs.getInt("id"), rs.getString("companie"),rs.getString("aeroportPlecare"),rs.getString("aeroportSosire"),rs.getString("dataPlecare"),rs.getString("dataSosire"),rs.getString("oraPlecare"),rs.getString("oraSosire"),rs.getInt("nrLocuri"));
    }
    public static List<ZborRezervat> citireZboruriRezervate(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<ZborRezervat> l= new ArrayList<>();
        ZborRezervat x= new ZborRezervat();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from zborurirezervate";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (ZborRezervat) createBeanZboruriRezervate(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(ZborRezervat i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateZboruriRezervate(List<ZborRezervat> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from zborurirezervate";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from zborurirezervate";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(ZborRezervat i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO zborurirezervate (idZr,idUser,idZbor,loc) " + "VALUES ( "+i.getIdZr()+","+i.getIdUser()+","+i.getIdZbor()+",'"+i.getLoc()+"')");
                st.executeUpdate("INSERT INTO zborurirezervate (idZr,idUser,idZbor,loc) " + "VALUES ( "+i.getIdZr()+","+i.getIdUser()+","+i.getIdZbor()+",'"+i.getLoc()+"')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanZboruriRezervate( ResultSet rs ) throws SQLException {
        return new ZborRezervat(rs.getInt("idZr"),rs.getInt("idUser"),rs.getInt("idZbor"),rs.getString("loc"));
    }
    public static List<Destinatie> citireDestinatii(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Destinatie> l= new ArrayList<>();
        Destinatie x= new Destinatie();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from destinatii";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (Destinatie) createBeanDestinatii(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(Destinatie i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateDestinatii(List<Destinatie> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from destinatii";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from destinatii";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(Destinatie i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO destinatii (id,nume,despre) " + "VALUES ( "+i.getId()+",'"+i.getNume()+"','"+i.getDespre()+"')");
               st.executeUpdate("INSERT INTO destinatii (id,nume,despre) " + "VALUES ( "+i.getId()+",'"+i.getNume()+"','"+i.getDespre()+"')");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanDestinatii( ResultSet rs ) throws SQLException {
        return new Destinatie(rs.getInt("id"), rs.getString("nume"),rs.getString("despre"));
    }
    public static List<Mesaj> citireMesaje(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Mesaj> l= new ArrayList<>();
        Mesaj x= new Mesaj();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from mesaje";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (Mesaj) createBeanMesaje(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(Mesaj i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateMesaje(List<Mesaj> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from mesaje";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from mesaje";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(Mesaj i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO mesaje (id,idZr,mesaj,ord) " + "VALUES ( "+i.getId()+","+i.getIdZr()+",'"+i.getMesaj()+"',"+i.getOrd()+")");
                st.executeUpdate("INSERT INTO mesaje (id,idZr,mesaj,ord) " + "VALUES ( "+i.getId()+","+i.getIdZr()+",'"+i.getMesaj()+"',"+i.getOrd()+")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanMesaje( ResultSet rs ) throws SQLException {
        return new Mesaj(rs.getInt("id"), rs.getInt("idZr"),rs.getString("mesaj"),rs.getInt("ord"));
    }
    public static List<Review> citireReviewuri(){
        /**
         * Aceasta metoda deserializeara obiectele de tip user
         *
         */
        List<Review> l= new ArrayList<>();
        Review x= new Review();
        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        var sql= "select * from reviewuri";

        try{
            Connection conn= DriverManager.getConnection(url,user,password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            ResultSet resultSet =statement.executeQuery(sql);
            while(resultSet.next()){
                x= (Review) createBeanReviewuri(resultSet);
                System.out.println("a");
                System.out.println(x);
                l.add(x);
                System.out.println("sss");
                for(Review i: l){
                    System.out.println(i);
                }
            }
            resultSet.close();
            statement.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return l;

    }
    public static void updateReviewuri(List<Review> l){
        /**
         * Aceasta metoda serializeara obiectele de tip user
         *
         */

        var url="jdbc:mysql://localhost:3306/p3";
        var user="root";
        var password="" ;
        String sql= "select * from reviewuri";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn.getCatalog());
            Statement statement = conn.createStatement();
            sql="delete from reviewuri";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.executeUpdate();
            Statement st = conn.createStatement();
            for(Review i: l) {
                System.out.println(i);
                System.out.println("INSERT INTO mesaje (id,idDest,mesaj,nrStele) " + "VALUES ( "+i.getId()+","+i.getIdDest()+",'"+i.getMesaj()+"',"+i.getNrStele()+")");
                st.executeUpdate("INSERT INTO reviewuri (id,idDest,mesaj,nrStele) " + "VALUES ( "+i.getId()+","+i.getIdDest()+",'"+i.getMesaj()+"',"+i.getNrStele()+")");

            }
            conn.close();
            //ResultSet resultSet = statement.executeQuery(sql);*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static Object createBeanReviewuri( ResultSet rs ) throws SQLException {
        return new Review(rs.getInt("id"), rs.getInt("idDest"),rs.getString("mesaj"),rs.getInt("nrStele"));
    }

}
