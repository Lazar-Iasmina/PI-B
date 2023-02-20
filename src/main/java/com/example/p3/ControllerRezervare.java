package com.example.p3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRezervare implements  Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    ChoiceBox<String> aPlecare;
    @FXML
    ChoiceBox<String> aSosire;
    @FXML
    DatePicker dataPlecare;
    @FXML
    DatePicker dataSosire;
    @FXML
    Label selPlecare;
    @FXML
    Label selSosire;
    @FXML
    RadioButton dus;
    @FXML
    RadioButton intors;
    @FXML
    Label radio;
    private List<String> listaDest=new ArrayList<>();
    private List<Destinatie>ld=new ArrayList<>();
    public void initialize(URL arg0, ResourceBundle arg1) {
        ld=citireDestinatii();
        for(Destinatie i: ld){
            System.out.println("$$$$"+i.getNume());
            listaDest.add(i.getNume());
        }
        aPlecare.getItems().addAll(listaDest);
        aPlecare.setOnAction(this::getFood);
        aSosire.getItems().addAll(listaDest);
        aSosire.setOnAction(this::getFood1);


    }
    public void getFood(ActionEvent e){
        String myFood=aPlecare.getValue();
        selPlecare.setText(myFood);
    }
    public void getFood1(ActionEvent e){
        String myFood=aSosire.getValue();
        selSosire.setText(myFood);
    }
    public void cautare(ActionEvent e) throws IOException{
        //root = FXMLLoader.load(getClass().getResource("Scena2.fxml"));
        String destinatie = selPlecare.getText();
        String aeroportSosire1 = selSosire.getText();
        LocalDate dataPlecare1 = dataPlecare.getValue();
        LocalDate dataSosire1 = dataSosire.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String dP = dataPlecare1.format(formatter);
        String dS = dataSosire1.format(formatter);
        System.out.println(destinatie+ aeroportSosire1+dP+dS);

        if(destinatie.equals("") || aeroportSosire1.equals("") || dataSosire1==null || dataPlecare1==null){
            root = FXMLLoader.load(getClass().getResource("Scena2.fxml"));
            //cautare(e);
        }
        else {

            if(dus.isSelected()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AlegereZborDus.fxml"));
                root = loader.load();
                ControllerAlegereZbor scene2Controller = loader.getController();
                scene2Controller.displayDestinatie(destinatie, aeroportSosire1, dP, dS);
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else if(intors.isSelected()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AlegereZborIntors.fxml"));
                root = loader.load();
                IntorsController scene2Controller = loader.getController();
                scene2Controller.displayDestinatie(destinatie, aeroportSosire1, dP, dS);
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
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
