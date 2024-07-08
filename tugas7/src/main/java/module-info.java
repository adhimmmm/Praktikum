module com.example.tugas7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.tugas7 to javafx.fxml;
    exports com.example.tugas7;
}