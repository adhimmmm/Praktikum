package org.example.tugasmodulke6.com.main;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tugasmodulke6.books.*;
import org.example.tugasmodulke6.data.Admin;
import org.example.tugasmodulke6.data.Student;

import java.util.ArrayList;

public class LibrarySystem {
    public static ArrayList<Book> daftarBuku = new ArrayList<>();
    public static ArrayList<Student> studentList = new ArrayList<>();

    public static void startLibrarySystem(Stage stage) {
        daftarBuku.add(new StoryBook("1", "HABBIT", 17, "Story", "Genos"));
        daftarBuku.add(new HistoryBook("2", "XNXX", 2, "History", "YAZID FIRDAUS"));
        daftarBuku.add(new HistoryBook("2", "woy", 2, "text", "YAZID aja"));


        studentList.add(new Student("202310370311377", "JORDI", "AKTOR", "SENI"));
        studentList.add(new Student("202310370311203", "MIA KHALIFA", "KESENIAN", "ART"));
        studentList.add(new Student("202310370311222", "vina", "fisip", "hi"));


        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("===== Library System =====");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button studentLoginButton = createButton("Login sebagai Mahasiswa", "#4CAF50");
        Button adminLoginButton = createButton("Login sebagai Admin", "#2196F3");
        Button exitButton = createButton("Keluar", "#9C27B0");

        studentLoginButton.setOnAction(event -> studentLogin(stage));
        adminLoginButton.setOnAction(event -> {
            try {
                new Admin().login(stage);
            } catch (Exception e) {
                showErrorDialog("Error", e.getMessage());
            }
        });
        exitButton.setOnAction(event -> stage.close());

        root.getChildren().addAll(label, studentLoginButton, adminLoginButton, exitButton);

        stage.setScene(scene);
        stage.setTitle("Library System");
        stage.show();
    }

    private static void studentLogin(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("Masukkan NIM : ");
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField nimField = new TextField();
        Button loginButton = createButton("Login", "#4CAF50");
        Button backButton = createButton("Kembali", "#2196F3");


        loginButton.setOnAction(event -> {
            String nimStudent = nimField.getText();
            if (nimStudent.length() == 15 && checkNim(nimStudent)) {
                Student student = new Student(nimStudent);
                student.login(stage);
            } else {
                showErrorDialog("Error", "Nim tidak terdaftar atau tidak valid!");
            }
        });

        backButton.setOnAction(event -> startLibrarySystem(stage));

        root.getChildren().addAll(label, nimField, loginButton, backButton);

        stage.setScene(scene);
    }

    private static void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean checkNim(String nim) {
        for (Student student : studentList) {
            if (student.getNim().equals(nim)) {
                return true;
            }
        }
        return false;
    }

    private static Button createButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        return button;
    }
}