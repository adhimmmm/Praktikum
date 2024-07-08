package com.example.tugas7;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recipe {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty ingredients;
    private final StringProperty recipe;
    private final StringProperty price;
    private final StringProperty imagePath;
    private final StringProperty stock; // tambahkan properti stock

    public Recipe(String id, String name, String ingredients, String recipe, String price, String imagePath, String stock) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.ingredients = new SimpleStringProperty(ingredients);
        this.recipe = new SimpleStringProperty(recipe);
        this.price = new SimpleStringProperty(price);
        this.imagePath = new SimpleStringProperty(imagePath);
        this.stock = new SimpleStringProperty(stock); // inisialisasi properti stock
    }

    // Getters and setters with StringProperty for binding
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }
    public StringProperty nameProperty() { return name; }

    public String getIngredients() { return ingredients.get(); }
    public void setIngredients(String ingredients) { this.ingredients.set(ingredients); }
    public StringProperty ingredientsProperty() { return ingredients; }

    public String getRecipe() { return recipe.get(); }
    public void setRecipe(String recipe) { this.recipe.set(recipe); }
    public StringProperty recipeProperty() { return recipe; }

    public String getPrice() { return price.get(); }
    public void setPrice(String price) { this.price.set(price); }
    public StringProperty priceProperty() { return price; }

    public String getImagePath() { return imagePath.get(); }
    public void setImagePath(String imagePath) { this.imagePath.set(imagePath); }
    public StringProperty imagePathProperty() { return imagePath; }

    public String getStock() { return stock.get(); }
    public void setStock(String stock) { this.stock.set(stock); }
    public StringProperty stockProperty() { return stock; }
}
