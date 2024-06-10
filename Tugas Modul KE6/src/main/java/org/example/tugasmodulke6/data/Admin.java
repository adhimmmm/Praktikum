package org.example.tugasmodulke6.data;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.tugasmodulke6.books.Book;
import org.example.tugasmodulke6.books.HistoryBook;
import org.example.tugasmodulke6.books.StoryBook;
import org.example.tugasmodulke6.books.TextBook;
import org.example.tugasmodulke6.exception.custom.IllegalAdminAccess;
import org.example.tugasmodulke6.util.iMenu;

import java.util.ArrayList;
import java.util.Scanner;

import static org.example.tugasmodulke6.com.main.LibrarySystem.*;

public class Admin extends User implements iMenu {

    Scanner scanner = new Scanner(System.in);

    public Admin() {
        super("admin");
    }

    public void login(Stage stage) throws IllegalAdminAccess {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setVgap(10);
        root.setHgap(10);

        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("===== HALAMAN ADMIN =====");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label userLabel = new Label("Masukkan Username (admin): ");
        TextField usernameField = new TextField();
        Label passLabel = new Label("Masukkan Password (admin): ");
        PasswordField passwordField = new PasswordField();
        Button loginButton = createButton("Login", "#EEC900");
        Button backButton = createButton("Kembali", "#FF6A6A");


        GridPane.setConstraints(label,0,0);
        GridPane.setColumnIndex(label,0);

        GridPane.setConstraints(userLabel, 0, 1);
        GridPane.setConstraints(usernameField, 1, 1);

        GridPane.setConstraints(passLabel, 0, 2);
        GridPane.setConstraints(passwordField, 1, 2);

        GridPane.setConstraints(loginButton, 0, 3);
        GridPane.setConstraints(backButton, 1, 3);
        //pppppppppppp

        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (isAdmin(username, password)) {
                showAdminMenu(stage);
            } else {
                showErrorDialog("Error", "Invalid credentials");
            }
        });

        backButton.setOnAction(event -> startLibrarySystem(stage));

        root.getChildren().addAll(label, userLabel, usernameField, passLabel, passwordField, loginButton, backButton);

        stage.setScene(scene);
    }

    private boolean isAdmin(String username, String password) {
        return username.equals("admin") && password.equals("admin");
    }

    private void showAdminMenu(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("===Menu Admin===");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create buttons with custom styles
        Button addStudentButton = createButton("Tambah Mahasiswa", "#4CAF50");
        Button displayStudentsButton = createButton("Tampilkan Mahasiswa", "#2196F3");
        Button inputBookButton = createButton("Input Buku", "#FFC107");
        Button displayBooksButton = createButton("Tampilkan Daftar Buku", "#FF5722");
        Button logoutButton = createButton("Logout", "#9C27B0");

        // Set button actions
        addStudentButton.setOnAction(event -> addStudent(stage));
        displayStudentsButton.setOnAction(event -> displayStudents(stage));
        inputBookButton.setOnAction(event -> inputBook(stage));
        displayBooksButton.setOnAction(event -> displayBooks(stage));
        logoutButton.setOnAction(event -> startLibrarySystem(stage));

        // Add buttons to the root VBox
        root.getChildren().addAll(label, addStudentButton, displayStudentsButton, inputBookButton, displayBooksButton, logoutButton);

        stage.setScene(scene);
    }

    private void addStudent(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);

        Label nameLabel = new Label("Masukkan Nama: ");
        TextField nameField = new TextField();
        Label nimLabel = new Label("Masukkan NIM: ");
        TextField nimField = new TextField();
        Label facultyLabel = new Label("Masukkan Fakultas: ");
        TextField facultyField = new TextField();
        Label studyProgramLabel = new Label("Masukkan Program Studi: ");
        TextField studyProgramField = new TextField();
        Button addButton = createButton("Tambah", "#4CAF50");
        Button backButton = createButton("Kembali", "#2196F3");

        addButton.setOnAction(event -> {
            String name = nameField.getText();
            String nim = nimField.getText();
            String faculty = facultyField.getText();
            String studyProgram = studyProgramField.getText();

            if (nim.length() == 15 && !checkNim(nim)) {
                studentList.add(new Student(nim, name, faculty, studyProgram));
                showAdminMenu(stage);
            } else {
                showErrorDialog("Error", "NIM tidak valid atau sudah terdaftar!");
            }
        });

        backButton.setOnAction(event -> showAdminMenu(stage));

        root.getChildren().addAll(nameLabel, nameField, nimLabel, nimField, facultyLabel, facultyField, studyProgramLabel, studyProgramField, addButton, backButton);

        stage.setScene(scene);
    }

    private void inputBook(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(new ScrollPane(root), 400, 300);

        Label titleLabel = new Label("Masukkan Judul Buku: ");
        TextField titleField = new TextField();
        Label authorLabel = new Label("Masukkan Author Buku: ");
        TextField authorField = new TextField();
        Label categoryLabel = new Label("Masukkan Category Buku: ");
        TextField categoryField = new TextField();
        Label stockLabel = new Label("Masukkan Stok Buku: ");
        TextField stockField = new TextField();
        Button addButton = createButton("Tambah", "#FFC107");
        Button backButton = createButton("Kembali", "#FF5722");

        addButton.setOnAction(event -> {
            String judul = titleField.getText();
            String author = authorField.getText();
            String category = categoryField.getText();
            int stok = Integer.parseInt(stockField.getText());

            daftarBuku.add(new Book(generateId("B"), judul, stok, category, author));
            showAdminMenu(stage);
        });

        backButton.setOnAction(event -> showAdminMenu(stage));

        root.getChildren().addAll(titleLabel, titleField, authorLabel, authorField, categoryLabel, categoryField, stockLabel, stockField, addButton, backButton);

        stage.setScene(scene);
    }

    private String generateId(String prefix) {
        int id = daftarBuku.size() + 1;
        return prefix + String.format("%03d", id);
    }

    private void displayBooks(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(new ScrollPane(root), 400, 300);

        for (Book book : daftarBuku) {
            root.getChildren().add(new Label("ID Buku: " + book.getIdBuku()));
            root.getChildren().add(new Label("Judul: " + book.getJudul()));
            root.getChildren().add(new Label("Author: " + book.getAuthor()));
            root.getChildren().add(new Label("Category: " + book.getCategory()));
            root.getChildren().add(new Label("Stok: " + book.getStok()));
            root.getChildren().add(new Label(""));
        }

        Button backButton = createButton("Kembali", "#9C27B0");
        backButton.setOnAction(event -> showAdminMenu(stage));

        root.getChildren().add(backButton);

        stage.setScene(scene);
    }

    private void displayStudents(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(new ScrollPane(root),400, 300);

        for (Student student : studentList) {
            root.getChildren().add(new Label("NIM: " + student.getNim()));
            root.getChildren().add(new Label("Nama: " + student.getName()));
            root.getChildren().add(new Label("Fakultas: " + student.getFaculty()));
            root.getChildren().add(new Label("Program Studi: " + student.getStudyProgram()));
            root.getChildren().add(new Label(""));
        }

        Button backButton = createButton("Kembali", "#9C27B0");
        backButton.setOnAction(event -> showAdminMenu(stage));

        root.getChildren().add(backButton);

        stage.setScene(scene);
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void menu() {

    }

    // Method to create custom styled buttons
    private Button createButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px;");
        return button;
    }
}