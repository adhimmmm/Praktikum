package com.example.tugas7;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Purchase {

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty paymentAmount;

    public Purchase(String id, String name, double price, double paymentAmount) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.paymentAmount = new SimpleDoubleProperty(paymentAmount);
    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public double getPaymentAmount() {
        return paymentAmount.get();
    }
}

