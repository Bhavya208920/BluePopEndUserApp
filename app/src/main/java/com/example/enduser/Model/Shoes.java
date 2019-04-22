package com.example.enduser.Model;

import java.io.Serializable;

public class Shoes implements Serializable {

    public int image;
    public String docId;
    public String id;
    public String size;
    public String name;
    public String price;
    public String color;

    public Shoes() {
    }

    public Shoes(int image, String docId, String id, String size, String name, String price, String color) {
        this.image = image;
        this.docId = docId;
        this.id = id;
        this.size = size;
        this.name = name;
        this.price = price;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Shoes{" +
                "image=" + image +
                ", docId='" + docId + '\'' +
                ", id='" + id + '\'' +
                ", size='" + size + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
