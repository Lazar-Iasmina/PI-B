module com.example.p3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires itextpdf;


    opens com.example.p3 to javafx.fxml;
    exports com.example.p3;

}