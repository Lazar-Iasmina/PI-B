package com.example.p3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerChatroomAdmin {
    private Stage stage;
    private Scene scene;
    private Parent root;
    List<Mesaj> l1= new ArrayList<>();
    String Zborul;
    String user;
    String zbor;
    @FXML
    ListView<String> mesaje;
    @FXML
    TextField mesaj;
    public void displayChatroom(String x){
        Zborul=x;
        try {
            /**
             * Aceasta metoda afiseaza toate mesajele dintre admin si un user despre un anumit zbor
             *
             */

           l1=citireMesaje();

            String uname1, pass1;
            uname1 = "user";
            pass1 = "1234";
            String[] splited = x.split("\\s+");
            user=splited[0];

            List<String> LM = new ArrayList<>();
            for (Mesaj i : l1) {
                System.out.println();
                if (i.getIdZr()==Integer.parseInt(user)) {
                    System.out.println("///*" );
                    LM.add(i.getMesaj());
                }
            }
            System.out.println("//////*" + LM);
            mesaje.getItems().addAll(LM);
        }catch (Exception e){
            e.printStackTrace();
        }




    }
    public void sendMesaj(ActionEvent e) throws IOException{
        /**
         * Aceasta metodade posibilitatea adminului sa dea mesaj userului
         *
         */
        String msg= mesaj.getText();
        mesaje.getItems().add("Employee : "+ msg);
        mesaj.setText("");
        l1.clear();
        l1=citireMesaje();
        String uname1,pass1;
        uname1="user";
        pass1="1234";

        List<String> lst=new ArrayList<>();
        int ok=0;
        int max=-1;
        for(Mesaj i: l1){
            if(i.getId()>=max){
                max=i.getId();
            }
        }
        max=max+1;
        Mesaj m=new Mesaj(max,Integer.parseInt(user),"Employee : "+msg,2);
        l1.add(m);
        updateMesaje(l1);

        System.out.println();
        for(Mesaj i: l1){
            System.out.println(i);
        }
    }
    public void refresh(ActionEvent e) throws IOException {
        /**
         * Aceasta metoda face refresh pt ca adminul si userul sa poata coresponda
         *
         */
        //mesaje.getItems().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatAdmin.fxml"));
        root = loader.load();
        ControllerChatroomAdmin scene2Controller = loader.getController();
        scene2Controller.displayChatroom(Zborul);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchtoAdmin1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void serializareMesaje(List<Mesaj> l, File f){
        try{
            FileOutputStream fos= new FileOutputStream(f);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            for(Mesaj i: l){
                oos.writeObject(i);
            }
            oos.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<Mesaj> deserializareMesaje(File f, int numar){
        List<Mesaj> l= new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            for(int i=0;i<numar;i++){
                l.add((Mesaj) ois.readObject());
            }
            ois.close();
            fis.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return l;
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
    public void switchtoScene1(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("meniuPrincipal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
