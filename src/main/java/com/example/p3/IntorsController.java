package com.example.p3;

import com.example.p3.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class IntorsController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    Label aleg;
    @FXML
    Label aleg1;
    @FXML
    ListView<String> listv;
    @FXML
    ListView<String> listv1;
    String ALeg;
    String ALeg1;

    public void displayDestinatie(String destinatie, String sosire, String plecare, String timpSosire){
        List<Zbor> l= new ArrayList<>();
        List<String> l2=new ArrayList<>();
        List<String> l3=new ArrayList<>();
        l=citireZbor();
        for(Zbor i: l) {
            System.out.println("****");
            System.out.println();
            if (i.getAeroportPLecare().equals(destinatie) && i.getAeroportSosire().equals(sosire) && i.getDataPlecare().equals(plecare)) {
                l2.add(i.toString());
                System.out.println(i);
            }
        }
        for(Zbor i: l) {
            System.out.println("****");
            System.out.println();
            if(i.getAeroportPLecare().equals(sosire) && i.getAeroportSosire().equals(destinatie) && i.getDataPlecare().equals(timpSosire)){
                l3.add(i.toString());
                System.out.println(i);
            }

        }

        //labelZboruri.setText(labelZboruri.getText()+"\n"+i.getaPlecare()+" "+i.getaSosire()+" "+i.getDataPlecare()+" "+i.getOraZbor()+" "+i.getPretZbor()+" lei");
        listv.getItems().addAll(l2);
        listv1.getItems().addAll(l3);
        listv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                ALeg=listv.getSelectionModel().getSelectedItem();
                aleg.setText(ALeg);
            }
        });
        listv1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                ALeg1=listv1.getSelectionModel().getSelectedItem();
                aleg1.setText(ALeg1);
            }
        });



    }
    public void rezervare(ActionEvent e) throws IOException, DocumentException {
        String alegere=aleg.getText();
        String alegere1=aleg1.getText();
        String uname1="";
        File myObj= new File("userPass.txt");
        Scanner myReader= new Scanner(myObj);
        while(myReader.hasNextLine()){
            uname1=myReader.nextLine();

        }
        int x=0;
        List<User> u=new ArrayList<>();
        u=citireUseri();
        for(User i: u){
            if(i.getEmail().equals(uname1)){
                x=i.getId();
            }
        }
        List<ZborRezervat> zb=new ArrayList<>();
        zb=citireZboruriRezervate();
        int max=-1;
        for(ZborRezervat i:zb){
            if(i.getIdZr()>=max){
                max=i.getIdZr();
            }
        }
        String s=aleg.getText();
        String[] arr=s.split(" ",2);
        String s1=aleg1.getText();
        String[] arr1=s1.split(" ",2);
        ZborRezervat z=new ZborRezervat(max+1,x,Integer.parseInt(arr[0]),"a1");
        ZborRezervat z1=new ZborRezervat(max+2,x,Integer.parseInt(arr1[0]),"a1");
        zb.add(z);
        zb.add(z1);
        updateZboruriRezervate(zb);


        u=citireUseri();
        for(User i: u){
            if(i.getEmail().equals(uname1)){
                x=i.getId();
            }
        }
        zb=citireZboruriRezervate();
         max=-1;
        for(ZborRezervat i:zb){
            if(i.getIdZr()>=max){
                max=i.getIdZr();
            }
        }
         s=aleg.getText();
         arr=s.split(" ",2);
         z=new ZborRezervat(max+1,x,Integer.parseInt(arr[0]),"a1");
        zb.add(z);
        updateZboruriRezervate(zb);
        ////////
        root = FXMLLoader.load(getClass().getResource("SuccesRezervare.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
        String file_name="C:\\Users\\Asus\\Desktop\\Pdf-uri exportate\\"+z.getIdZr()+z.getIdUser()+".pdf";
        String file_name1="C:\\Users\\Asus\\Desktop\\Pdf-uri exportate\\"+z.getIdZr()+z.getIdUser()+"-intors.pdf";
        List<Zbor> zbbb=new ArrayList<>();
        zbbb=citireZbor();
        List<User> us=new ArrayList<>();
        us=citireUseri();
        Zbor zz=new Zbor();
        User uu= new User();
        for(Zbor i:zbbb){
            if(i.getId()==z.getIdZbor()){
                zz=i;
            }
        }
        for(User i:us){
            if(i.getId()==z.getIdUser()){
                uu=i;
            }
        }


        Document doc=new Document();
        Document doc1=new Document();
        PdfWriter c= PdfWriter.getInstance(doc,new FileOutputStream(file_name));
        doc.open();
        Paragraph para=new Paragraph("NumeClient : "+uu.getNume()+"\n");
        doc.add(para);
        para=new Paragraph("PrenumeClient : "+uu.getPrenume()+"\n");
        doc.add(para);
        para=new Paragraph("Aeroport plecare : "+zz.getAeroportPLecare()+"\n");
        doc.add(para);
        para=new Paragraph("Aeroport sosire : "+zz.getAeroportSosire()+"\n");
        doc.add(para);
        para=new Paragraph("Data Plecare : "+zz.getDataPlecare()+"\n");
        doc.add(para);
        para=new Paragraph("Ora Sosire : "+zz.getOraPlecare()+"\n");
        doc.add(para);
        para=new Paragraph("Ora Sosire : "+zz.getOraSosire()+"\n");
        doc.add(para);
        para=new Paragraph("Pret Zbor :"+zz.getNrLocuri()+"\n");
        //doc.add(Image.getInstance("C:\\Users\\Asus\\Desktop\\demo1 - Copy\\src\\main\\resources\\com\\example\\demo1\\"+zb.getaSosire()+".jpg"));
        para=new Paragraph("\n\n\n");
        doc.add(para);
        Random x1=new Random();
        int y=x1.nextInt(100);
        String myString=zz.getAeroportPLecare()+zz.getAeroportSosire()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getAeroportSosire()+zz.getAeroportPLecare()+zz.getAeroportSosire()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getAeroportSosire()+zz.getAeroportSosire()+zz.getAeroportPLecare();
        Barcode128 code128=new Barcode128();
        code128.setCode(myString.trim());
        code128.setCodeType(Barcode128.CODE128);
        PdfContentByte cb=c.getDirectContent();
        Image code128Image=code128.createImageWithBarcode(cb,null,null);
        doc.add(code128Image);
        //doc.add(Image.getInstance("C:\\Users\\Asus\\Desktop\\demo1 - Copy\\src\\main\\resources\\com\\example\\demo1\\qr"+y%3+".jpg"));
        doc.close();

        PdfWriter c1= PdfWriter.getInstance(doc1,new FileOutputStream(file_name1));
        doc1.open();
        Paragraph para1=new Paragraph("NumeClient : "+uu.getNume()+"\n");
        doc1.add(para1);
        para1=new Paragraph("PrenumeClient : "+uu.getPrenume()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Aeroport plecare : "+zz.getAeroportPLecare()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Aeroport sosire : "+zz.getAeroportSosire()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Data Plecare : "+zz.getDataPlecare()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Ora Sosire : "+zz.getOraPlecare()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Ora Sosire : "+zz.getOraSosire()+"\n");
        doc1.add(para1);
        para1=new Paragraph("Pret Zbor :"+zz.getNrLocuri()+"\n");
        //doc.add(Image.getInstance("C:\\Users\\Asus\\Desktop\\demo1 - Copy\\src\\main\\resources\\com\\example\\demo1\\"+zb.getaSosire()+".jpg"));
        para1=new Paragraph("\n\n\n");
        doc1.add(para1);
        Random x11=new Random();
        int y1=x11.nextInt(100);
        String myString1=zz.getAeroportPLecare()+zz.getAeroportSosire()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getAeroportSosire()+zz.getAeroportPLecare()+zz.getAeroportSosire()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getOraPlecare()+zz.getAeroportSosire()+zz.getAeroportSosire()+zz.getAeroportPLecare();
        Barcode128 code1281=new Barcode128();
        code1281.setCode(myString1.trim());
        code1281.setCodeType(Barcode128.CODE128);
        PdfContentByte cb1=c1.getDirectContent();
        Image code128Image1=code128.createImageWithBarcode(cb1,null,null);
        doc1.add(code128Image1);
        //doc.add(Image.getInstance("C:\\Users\\Asus\\Desktop\\demo1 - Copy\\src\\main\\resources\\com\\example\\demo1\\qr"+y%3+".jpg"));
        doc1.close();

        String recepient=uu.getEmail();
        String about="Bilet avion ";
        String mesaj="Aveti atasat biletul de avion";
        try {
            boolean flag=false;
            System.out.println("Preparing to sent email");
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            String myAccountEmail = "lazar.iasmina7@gmail.com";
            String password = "sxhzrxvewpebauts";
            String myAccountEmail1 = "lazar.iasmina7";
            //boon
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail1,password);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(about);
            message.setText(mesaj);
            MimeBodyPart part1 = new MimeBodyPart();
            part1.setText("Buna ziua!\nAveti atasate aici biletele de avion!\nVa multumim!\n");
            File file=new File("C:\\Users\\Asus\\Desktop\\Pdf-uri exportate\\"+z.getIdZr()+""+z.getIdUser()+".pdf");
            File file1=new File("C:\\Users\\Asus\\Desktop\\Pdf-uri exportate\\"+z.getIdZr()+""+z.getIdUser()+"-intors.pdf");
            MimeBodyPart part2 = new MimeBodyPart();
            part2.attachFile(file);
            MimeBodyPart part3 = new MimeBodyPart();
            part3.attachFile(file1);
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(part1);
            mimeMultipart.addBodyPart(part2);
            mimeMultipart.addBodyPart(part3);
            message.setContent(mimeMultipart);

            Transport.send(message);
            flag=true;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("mesaj trimis");
        ///////////////////////

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
