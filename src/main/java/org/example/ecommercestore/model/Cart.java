package org.example.ecommercestore.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private int id;
    private String productName;
    private double price;
    private int quantity;

    // List to hold products in the cart
    private List<products> items = new ArrayList<>();

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public List<products> getItems() {
        return items;
    }

    public void setItems(List<products> items) {
        this.items = items;
    }

    public void setName(String name) {
        this.productName = name;
    }
}
