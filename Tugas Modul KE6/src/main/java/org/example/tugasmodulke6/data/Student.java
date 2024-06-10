package org.example.tugasmodulke6.data;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.example.tugasmodulke6.books.Book;
import org.example.tugasmodulke6.data.User;
import org.example.tugasmodulke6.util.iMenu;

import java.util.ArrayList;

import static org.example.tugasmodulke6.com.main.LibrarySystem.*;

public class Student extends User implements iMenu {

    private String nim;
    private String faculty;
    private String studyProgram;
    private ArrayList<Book> borrowedBooks;
    public Student(String nim) {
        super("student");
        this.nim = nim;
        this.borrowedBooks = new ArrayList<>();
    }

    public Student(String nim, String name, String faculty, String studyProgram) {
        super(name);
        this.nim = nim;
        this.faculty = faculty;
        this.studyProgram = studyProgram;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getNim() {
        return nim;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    @Override
    public void menu() {
        // Ini adalah implementasi dari metode menu() yang diperlukan oleh iMenu
        // Anda bisa menempatkan kode yang ingin dijalankan ketika menu() dipanggil
    }

    public void login(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("===Menu Mahasiswa===");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button borrowBookButton = createButton("Pinjam Buku", "#2196F3"); // Biru
        Button displayBooksButton = createButton("Tampilkan Daftar Buku", "#FF9800"); // Jingga
        Button displayBorrowedBooksButton = createButton("Tampilkan Buku yang Dipinjam", "#9C27B0"); // Ungu
        Button logoutButton = createButton("Logout", "#4CAF50"); // Hijau

        borrowBookButton.setOnAction(event -> borrowBook(stage));
        displayBooksButton.setOnAction(event -> displayBooks(stage));
        displayBorrowedBooksButton.setOnAction(event -> displayBorrowedBooks(stage));
        logoutButton.setOnAction(event -> startLibrarySystem(stage));

        root.getChildren().addAll(label, borrowBookButton, displayBooksButton, displayBorrowedBooksButton, logoutButton);

        stage.setScene(scene);
    }

    private void borrowBook(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 400, 300);

        Label label = new Label("Masukkan ID buku yang ingin dipinjam: ");
        TextField idBukuField = new TextField();
        Button borrowButton = createButton("Pinjam", "#FF5722"); // Merah
        Button backButton = createButton("Kembali", "#00BCD4"); // Biru Muda

        borrowButton.setOnAction(event -> {
            String idBuku = idBukuField.getText();
            Book bookToBorrow = null;

            for (Book book : daftarBuku) {
                if (book.getIdBuku().equals(idBuku)) {
                    bookToBorrow = book;
                    break;
                }
            }

            if (bookToBorrow!= null) {
                if (bookToBorrow.getStok() > 0) {
                    bookToBorrow.setStok(bookToBorrow.getStok() - 1);
                    borrowedBooks.add(bookToBorrow); // Add to borrowed books list
                    showSuccessDialog("Success", "Buku " + bookToBorrow.getJudul() + " berhasil dipinjam.");
                } else {
                    showErrorDialog("Error", "Stok buku " + bookToBorrow.getJudul() + " sedang kosong.");
                }
            } else {
                showErrorDialog("Error", "Buku dengan ID " + idBuku + " tidak ditemukan.");
            }
        });

        backButton.setOnAction(event -> login(stage));

        root.getChildren().addAll(label, idBukuField, borrowButton, backButton);

        stage.setScene(scene);
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

        Button backButton = createButton("Kembali", "#00BCD4"); // Biru Muda
        backButton.setOnAction(event -> login(stage));

        root.getChildren().add(backButton);

        stage.setScene(scene);
    }

    private void displayBorrowedBooks(Stage stage) {
        VBox root = new VBox(10);
        Scene scene = new Scene(new ScrollPane(root), 400, 300);

        for (Book book : borrowedBooks) {
            root.getChildren().add(new Label("ID Buku: " + book.getIdBuku()));
            root.getChildren().add(new Label("Judul: " + book.getJudul()));
            root.getChildren().add(new Label("Author: " + book.getAuthor()));
            root.getChildren().add(new Label("Category: " + book.getCategory()));
            root.getChildren().add(new Label("Stok: " + book.getStok()));
            root.getChildren().add(new Label(""));
        }

        Button backButton = createButton("Kembali", "#00BCD4");
        backButton.setOnAction(event -> login(stage));

        root.getChildren().add(backButton);

        stage.setScene(scene);
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Button createButton(String text, String backgroundColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-font-size: 16px; -fx-border-radius: 5px;");
        return button;
    }

}