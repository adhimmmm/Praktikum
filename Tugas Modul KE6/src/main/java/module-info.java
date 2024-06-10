module org.example.tugasmodulke6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tugasmodulke6 to javafx.fxml;
    exports org.example.tugasmodulke6;
}