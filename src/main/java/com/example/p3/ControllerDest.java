package com.example.p3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerDest {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ImageView imagdest;
    @FXML
    TabPane tabPane;
    @FXML
    Tab tab1;
    @FXML
    Tab tab2;
    @FXML
    Label nume;
    // @FXML
    // Button rezerva;
    @FXML
    ListView<String> reviewuri;
    @FXML
    Label despre;
    @FXML
    BarChart<String,Integer> statistica;
    @FXML private ImageView getImagdest;
    public void displayDestinatie(String x){

        List<Destinatie> l= new ArrayList<>();
        l=citireDestinatii();
        List<Review> l1= new ArrayList<>();
        l1=citireReviewuri();
        List<String>l2=new ArrayList<>();
        for(Destinatie i: l){
            int r1=0,r2=0,r3=0,r4=0,r5=0;
            if(i.getNume().equals(x)) {
                despre.setText(i.getDespre());
                nume.setText(i.getNume());
                Image photo;
                photo = new Image("C:\\Users\\Asus\\Desktop\\p3\\src\\main\\resources\\com\\example\\p3\\"+x+".jpg");
                imagdest.setImage(photo);


                for(Review j: l1) {
                    if(j.getIdDest()==i.getId()) {
                        l2.add(j.getMesaj());
                        if(j.getNrStele()==1) r1=r1+1;
                        if(j.getNrStele()==2) r2=r2+1;
                        if(j.getNrStele()==3) r3=r3+1;
                        if(j.getNrStele()==4) r4=r4+1;
                        if(j.getNrStele()==5) r5=r5+1;
                    }


                }
                reviewuri.getItems().addAll(l2);
                XYChart.Series series1=new XYChart.Series();
                series1.setName("Statistica Satisfactie Destinatie");
                series1.getData().add(new XYChart.Data("1",r1));
                series1.getData().add(new XYChart.Data("2",r2));
                series1.getData().add(new XYChart.Data("3",r3));
                series1.getData().add(new XYChart.Data("4",r4));
                series1.getData().add(new XYChart.Data("5",r5));
                statistica.getData().addAll(series1);
            }


        }
    }
    public void switchtoScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("meniuPrincipal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoRezervare(ActionEvent e) throws IOException{
        root = FXMLLoader.load(getClass().getResource("meniurezervare.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
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
