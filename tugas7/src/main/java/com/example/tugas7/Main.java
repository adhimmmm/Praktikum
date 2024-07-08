package com.example.tugas7;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

    private Stage primaryStage;
    private ObservableList<Recipe> recipeList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;


        StackPane welcomePane = new StackPane();
        Scene welcomeScene = new Scene(welcomePane, 600, 500);


        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));


        Label welcomeLabel = new Label("Selamat Datang");
        welcomeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);


        Button adminButton = new Button("Login as Admin");
        Button consumerButton = new Button("Login as Consumer");


        adminButton.setStyle("-fx-background-color: #FFFF00; -fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");
        consumerButton.setStyle("-fx-background-color: #FFFF00; -fx-text-fill: #000000; -fx-font-size: 18px; -fx-font-weight: bold;");


        buttonBox.getChildren().addAll(adminButton, consumerButton);


        vbox.getChildren().addAll(welcomeLabel, buttonBox);


        welcomePane.getChildren().add(vbox);


        adminButton.setOnAction(e -> showAdminLoginDialog());
        consumerButton.setOnAction(e -> showConsumerScreen());


        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Welcome");
        primaryStage.show();
    }

    private void showAdminLoginDialog() {
        // Membuat untuk masukkan username dan password
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Admin Login");
        dialog.setHeaderText("Enter username and password");

        // Membuat field untuk username dan password
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();

        VBox content = new VBox(10);
        content.getChildren().addAll(
                new Label("Username:"),
                usernameField,
                new Label("Password:"),
                passwordField
        );

        dialog.getDialogPane().setContent(content);

        // Menambahkan tombol Login dan Cancel
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Validasi input ketika tombol Login ditekan
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usernameField.getText(), passwordField.getText());
            }
            return null;
        });

        // Menampilkan dan menanggapi hasilnya
        dialog.showAndWait().ifPresent(result -> {
            String username = result.getKey();
            String password = result.getValue();

            // cek username dan password
            if ("admin".equals(username) && "password".equals(password)) {
                showAlert("Login successful", Alert.AlertType.INFORMATION);
                showAdminScreen();
            } else {
                showAlert("Invalid username or password", Alert.AlertType.ERROR);
            }
        });
    }

    private void showAdminScreen() {
        AdminScreen adminScreen = new AdminScreen(primaryStage, recipeList);
        adminScreen.show();
    }

    private void showConsumerScreen() {
        ConsumerScreen consumerScreen = new ConsumerScreen(primaryStage, recipeList);
        consumerScreen.show();
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
