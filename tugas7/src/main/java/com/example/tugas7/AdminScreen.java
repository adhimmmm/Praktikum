package com.example.tugas7;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdminScreen {

    private Stage stage;
    private ObservableList<Recipe> recipeList;
    private Recipe selectedRecipe;
    private TextField stockField;

    public AdminScreen(Stage stage, ObservableList<Recipe> recipeList) {
        this.stage = stage;
        this.recipeList = recipeList;
    }

    public void show() {
        StackPane adminPane = new StackPane();
        Scene adminScene = new Scene(adminPane, 800, 600);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));

        Label idLabel = new Label("ID:");
        TextField idField = new TextField();

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label ingredientsLabel = new Label("Ingredients:");
        TextArea ingredientsArea = new TextArea();

        Label recipeLabel = new Label("Recipe:");
        TextArea recipeArea = new TextArea();

        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField();

        Label stockLabel = new Label("Stock:");
        stockField = new TextField();

        Label imageLabel = new Label("Image:");
        Button imageButton = new Button("Choose Image");
        ImageView imageView = new ImageView();

        //fungsi untuk tombol image
        imageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
            }
        });

        // buat tombol untuk Tambah, Edit, dan Hapus
        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button backButton = new Button("Kembali");
        Button viewButton = new Button("Lihat");

        // fungsi untuk add Button
        addButton.setOnAction(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String ingredients = ingredientsArea.getText();
            String recipe = recipeArea.getText();
            String price = priceField.getText();
            String stock = stockField.getText();
            Image image = imageView.getImage();
            String imagePath = image != null ? image.getUrl() : null;

            if (id.isEmpty() || name.isEmpty() || ingredients.isEmpty() || recipe.isEmpty() || price.isEmpty() || stock.isEmpty() || imagePath == null) {
                showAlert("Please fill in all fields and choose an image.", Alert.AlertType.ERROR);
            } else {
                Recipe newRecipe = new Recipe(id, name, ingredients, recipe, price, imagePath, stock);
                recipeList.add(newRecipe);

                clearFields(idField, nameField, ingredientsArea, recipeArea, priceField, imageView, stockField);
                showAlert("Data added successfully!", Alert.AlertType.INFORMATION);
            }
        });

        // fungsi tombol edit
        editButton.setOnAction(e -> {
            if (selectedRecipe != null) {
                String id = idField.getText();
                String name = nameField.getText();
                String ingredients = ingredientsArea.getText();
                String recipe = recipeArea.getText();
                String price = priceField.getText();
                String stock = stockField.getText();
                Image image = imageView.getImage();
                String imagePath = image != null ? image.getUrl() : null;

                if (id.isEmpty() || name.isEmpty() || ingredients.isEmpty() || recipe.isEmpty() || price.isEmpty() || stock.isEmpty() || imagePath == null) {
                    showAlert("Please fill in all fields and choose an image.", Alert.AlertType.ERROR);
                } else {
                    selectedRecipe.setId(id);
                    selectedRecipe.setName(name);
                    selectedRecipe.setIngredients(ingredients);
                    selectedRecipe.setRecipe(recipe);
                    selectedRecipe.setPrice(price);
                    selectedRecipe.setImagePath(imagePath);
                    selectedRecipe.setStock(stock);

                    clearFields(idField, nameField, ingredientsArea, recipeArea, priceField, imageView, stockField);
                    showAlert("Data edited successfully!", Alert.AlertType.INFORMATION);
                }
            } else {
                showAlert("No recipe selected for editing.", Alert.AlertType.WARNING);
            }
        });

        // fungsi tombol delete
        deleteButton.setOnAction(e -> {
            if (selectedRecipe != null) {
                recipeList.remove(selectedRecipe);
                clearFields(idField, nameField, ingredientsArea, recipeArea, priceField, imageView, stockField);
                showAlert("Data deleted successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("No recipe selected for deletion.", Alert.AlertType.WARNING);
            }
        });

        // fungsi tombol back
        backButton.setOnAction(e -> {
            Main main = new Main();
            main.start(stage);
        });

        // fungsi tombol view
        viewButton.setOnAction(e -> {
            ConsumerScreen consumerScreen = new ConsumerScreen(stage, recipeList);
            consumerScreen.show();
        });

        //tabel view
        TableView<Recipe> tableView = new TableView<>(recipeList);
        TableColumn<Recipe, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        idColumn.setPrefWidth(50);

        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setPrefWidth(100);

        TableColumn<Recipe, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(cellData -> cellData.getValue().ingredientsProperty());
        ingredientsColumn.setPrefWidth(225);

        TableColumn<Recipe, String> recipeColumn = new TableColumn<>("Recipe");
        recipeColumn.setCellValueFactory(cellData -> cellData.getValue().recipeProperty());
        recipeColumn.setPrefWidth(225);

        TableColumn<Recipe, String> priceColumn = new TableColumn<>("Price $");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        priceColumn.setPrefWidth(50);

        TableColumn<Recipe, String> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStock()));
        stockColumn.setPrefWidth(50);

        TableColumn<Recipe, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imagePathProperty());
        imageColumn.setPrefWidth(100);

        tableView.getColumns().addAll(idColumn, nameColumn, ingredientsColumn, recipeColumn, priceColumn, stockColumn, imageColumn);

        // Menangani pemilihan baris
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRecipe = newSelection;
                idField.setText(selectedRecipe.getId());
                nameField.setText(selectedRecipe.getName());
                ingredientsArea.setText(selectedRecipe.getIngredients());
                recipeArea.setText(selectedRecipe.getRecipe());
                priceField.setText(selectedRecipe.getPrice());
                stockField.setText(selectedRecipe.getStock());
                if (selectedRecipe.getImagePath() != null) {
                    Image image = new Image(selectedRecipe.getImagePath());
                    imageView.setImage(image);
                } else {
                    imageView.setImage(null);
                }
            }
        });


        gridPane.add(idLabel, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameField, 1, 1);
        gridPane.add(ingredientsLabel, 0, 2);
        gridPane.add(ingredientsArea, 1, 2);
        gridPane.add(recipeLabel, 0, 3);
        gridPane.add(recipeArea, 1, 3);
        gridPane.add(priceLabel, 0, 4);
        gridPane.add(priceField, 1, 4);
        gridPane.add(stockLabel, 0, 5);
        gridPane.add(stockField, 1, 5);
        gridPane.add(imageLabel, 0, 6);
        gridPane.add(imageButton, 1, 6);

        VBox imageBox = new VBox(10);
        imageBox.setAlignment(Pos.BASELINE_LEFT);
        imageBox.getChildren().add(imageView);
        gridPane.add(imageBox, 1, 7);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, addButton, editButton, deleteButton, viewButton);

        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.TOP_CENTER);
        mainBox.setPadding(new Insets(20));
        mainBox.getChildren().addAll(gridPane, buttonBox, tableView);

        adminPane.getChildren().add(mainBox);
        stage.setScene(adminScene);
        stage.setTitle("Admin Panel");
        stage.show();
    }

    private void clearFields(TextField idField, TextField nameField, TextArea ingredientsArea, TextArea recipeArea, TextField priceField, ImageView imageView, TextField stockField) {
        idField.clear();
        nameField.clear();
        ingredientsArea.clear();
        recipeArea.clear();
        priceField.clear();
        stockField.clear();
        imageView.setImage(null);
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.ERROR ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
