package com.eduard.shoppinglist;

public class ShoppingListItem {
    String name;
    int quantity;
    boolean completed;

    public ShoppingListItem(String name, int quantity, boolean completed) {
        this.name=name;
        this.quantity=quantity;
        this.completed=completed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
