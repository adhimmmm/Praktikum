package com.example.tugas7;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConsumerScreen {

    private Stage stage;
    private ObservableList<Recipe> recipeList;
    private List<Recipe> selectedItems = new ArrayList<>();
    private ObservableList<Purchase> purchaseHistory = FXCollections.observableArrayList();

    // Label untuk menampilkan item yang dipilih
    private Label selectedItemLabel;
    private Label totalLabel;

    public ConsumerScreen(Stage stage, ObservableList<Recipe> recipeList) {
        this.stage = stage;
        this.recipeList = recipeList;
    }

    public void show() {
        StackPane consumerPane = new StackPane();
        Scene consumerScene = new Scene(consumerPane, 800, 600);

        TableView<Recipe> tableView = new TableView<>(recipeList);

        TableColumn<Recipe, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(50);

        TableColumn<Recipe, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(100);

        TableColumn<Recipe, String> ingredientsColumn = new TableColumn<>("Ingredients");
        ingredientsColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        ingredientsColumn.setPrefWidth(225);

        TableColumn<Recipe, String> recipeColumn = new TableColumn<>("Recipe");
        recipeColumn.setCellValueFactory(new PropertyValueFactory<>("recipe"));
        recipeColumn.setPrefWidth(225);

        TableColumn<Recipe, Double> priceColumn = new TableColumn<>("Price $");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(50);

        TableColumn<Recipe, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        stockColumn.setPrefWidth(50);

        TableColumn<Recipe, String> imageColumn = new TableColumn<>("Image");
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
        imageColumn.setPrefWidth(100);

        tableView.getColumns().addAll(idColumn, nameColumn, ingredientsColumn, recipeColumn, priceColumn, stockColumn, imageColumn);

        Button backButton = new Button("Kembali");
        backButton.setOnAction(e -> {
            Main main = new Main();
            main.start(stage);
        });

        //untuk username dan password
        Button adminButton = new Button("Admin");
        adminButton.setOnAction(e -> {
            // Membuat dialog untuk memasukkan username
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Login Admin");
            usernameDialog.setHeaderText("Masukkan Username");
            usernameDialog.setContentText("Username:");

            // Menampilkan dialog dan menangkap input username
            String username = usernameDialog.showAndWait().orElse("");

            // Membuat dialog untuk memasukkan password
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Login Admin");
            passwordDialog.setHeaderText("Masukkan Password");
            passwordDialog.setContentText("Password:");

            // Menampilkan dialog dan menangkap input password
            String password = passwordDialog.showAndWait().orElse("");

            // Memeriksa apakah username dan password benar
            if ("admin".equals(username) && "password".equals(password)) {
                AdminScreen adminScreen = new AdminScreen(stage, recipeList);
                adminScreen.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Masukkan username dan password yang benar");
                alert.showAndWait();
            }
        });

        // Panel untuk pembayaran
        GridPane paymentPane = new GridPane();
        paymentPane.setHgap(10);
        paymentPane.setVgap(10);
        paymentPane.setPadding(new Insets(20));

        // menampilkan data item yang dibeli
        Label data = new Label("Data Barang: ");
        data.setStyle("-fx-font-weight: bold;");

        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price $:");
        Label totalTitleLabel = new Label("Total:");

        Label idValueLabel = new Label();
        Label nameValueLabel = new Label();
        Label priceValueLabel = new Label();
        totalLabel = new Label("$0.00");

        // Label yang menampilkan informasi item yang dipilih
        selectedItemLabel = new Label();
        selectedItemLabel.setPadding(new Insets(10));

        // Input untuk jumlah pembayaran
        TextField paymentField = new TextField();
        paymentField.setPromptText("Enter payment amount");

        // Tombol untuk membayar
        Button payButton = new Button("Bayar");
        payButton.setOnAction(e -> {
            String paymentText = paymentField.getText();
            try {
                double paymentAmount = Double.parseDouble(paymentText);
                double total = calculateTotal();
                if (paymentAmount >= total) {
                    // Menampilkan dialog pembayaran berhasil
                    showPaymentSuccessDialog(total, paymentAmount);

                    // Mengurangi stok dari selectedItems
                    for (Recipe selectedRecipe : selectedItems) {
                        int currentStock = Integer.parseInt(selectedRecipe.getStock());
                        if (currentStock > 0) {
                            selectedRecipe.setStock(String.valueOf(currentStock - 1));

                            // Mengupdate item di TableView
                            int index = tableView.getItems().indexOf(selectedRecipe);
                            tableView.getItems().set(index, selectedRecipe);

                            // Tambahkan ke riwayat pembelian
                            purchaseHistory.add(new Purchase(selectedRecipe.getId(), selectedRecipe.getName(), Double.parseDouble(selectedRecipe.getPrice()), paymentAmount));
                        } else {
                            System.out.println("Stok sudah habis untuk item ini.");
                        }
                    }

                    // Kosongkan selectedItems setelah pembayaran
                    selectedItems.clear();
                    updateTotalLabel();

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText(null);
                    alert.setContentText("Jumlah pembayaran tidak mencukupi.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Masukkan jumlah pembayaran yang valid.");
                alert.showAndWait();
            }
        });

        // Tombol untuk menambah item yang dipilih ke daftar
        Button addButton = new Button("Tambah");
        addButton.setOnAction(e -> {
            Recipe selectedRecipe = tableView.getSelectionModel().getSelectedItem();
            if (selectedRecipe != null) {
                selectedItems.add(selectedRecipe);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Item Ditambahkan");
                alert.setHeaderText(null);
                alert.setContentText("Item " + selectedRecipe.getName() + " ditambahkan.");
                alert.showAndWait();
                updateTotalLabel();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Pilih item terlebih dahulu.");
                alert.showAndWait();
            }
        });

        // Tombol untuk menampilkan riwayat pembelian
        Button historyButton = new Button("Riwayat Pembelian");
        historyButton.setOnAction(e -> showPurchaseHistory());

        // Menambahkan elemen ke paymentPane
        paymentPane.add(data, 0, 0, 2, 1);
        paymentPane.addRow(1, idLabel, idValueLabel);
        paymentPane.addRow(2, nameLabel, nameValueLabel);
        paymentPane.addRow(3, priceLabel, priceValueLabel);
        paymentPane.addRow(4, totalTitleLabel, totalLabel);
        paymentPane.addRow(5, paymentField, payButton);

        // Mendapatkan item yang dipilih dari tabel
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idValueLabel.setText(newSelection.getId());
                nameValueLabel.setText(newSelection.getName());
                priceValueLabel.setText(newSelection.getPrice());
            }
        });


        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(backButton, adminButton, addButton, historyButton);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableView, selectedItemLabel, paymentPane, buttonBox); // Menambahkan selectedItemLabel dan buttonBox ke VBox
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        consumerPane.getChildren().add(vbox);
        stage.setScene(consumerScene);
        stage.setTitle("Consumer View");
        stage.show();
    }

    private double calculateTotal() {
        double total = 0;
        for (Recipe item : selectedItems) {
            total += Double.parseDouble(item.getPrice());
        }
        return total;
    }

    private void updateTotalLabel() {
        double total = calculateTotal();
        totalLabel.setText("$" + String.format("%.2f", total));
    }

    private void showPaymentSuccessDialog(double total, double paymentAmount) {
        // Menampilkan dialog pembayaran sukses
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Payment Success");
        alert.setHeaderText(null);
        if (paymentAmount == total) {
            alert.setContentText("Pembayaran sebesar $" + paymentAmount + " berhasil.");
            alert.showAndWait();
        } else if (paymentAmount > total) {
            double change = paymentAmount - total;
            alert.setContentText("Pembayaran sebesar $" + paymentAmount + " berhasil. Kembalian: $" + String.format("%.2f", change));
            alert.showAndWait();
        }
    }

    private void showPurchaseHistory() {
        // Membuat tabel untuk riwayat pembelian
        TableView<Purchase> historyTable = new TableView<>(purchaseHistory);

        TableColumn<Purchase, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(100);

        TableColumn<Purchase, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(200);

        TableColumn<Purchase, Double> priceColumn = new TableColumn<>("Price $");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setPrefWidth(100);

        TableColumn<Purchase, Double> paymentAmountColumn = new TableColumn<>("Payment Amount $");
        paymentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        paymentAmountColumn.setPrefWidth(100);

        historyTable.getColumns().addAll(idColumn, nameColumn, priceColumn, paymentAmountColumn);


        VBox vbox = new VBox();
        vbox.getChildren().addAll(historyTable);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Scene historyScene = new Scene(vbox, 600, 400);
        Stage historyStage = new Stage();
        historyStage.setScene(historyScene);
        historyStage.setTitle("Purchase History");
        historyStage.show();
    }
}
