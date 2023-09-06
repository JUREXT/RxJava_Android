package com.example.rxjava3.model;

public class Item {
    int id;

    public Item(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Created Item{" +
                "id=" + id +
                '}';
    }
}
