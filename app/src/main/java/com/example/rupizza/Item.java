package com.example.rupizza;

import java.util.ArrayList;

public class Item {
    private String image;
    private ArrayList<Topping> toppings;

    public Item(String image, ArrayList<Topping> toppings) {
        this.image = image;
        this.toppings = toppings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }
}
