package com.example.rupizza;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Item> itemsList;
    public recyclerAdapter(ArrayList<Item> itemsList){
        this.itemsList = itemsList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView pizzaImage;
        private ListView toppingsList;

        public MyViewHolder(final View view) {
            super(view);
            pizzaImage = view.findViewById(R.id.pizzaImage);
            toppingsList = view.findViewById(R.id.toppingsList);
        }
    }

    @NonNull
    @Override
    public recyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.MyViewHolder holder, int position) {
        int[] images = {
                R.drawable.deluxepizza, R.drawable.supremepizza, R.drawable.meatzza,
                R.drawable.seafoodpizza, R.drawable.pepperonipizza, R.drawable.cheeselovers,
                R.drawable.grilledchickenpizza, R.drawable.chickenalfredopizza, R.drawable.hawaiianpizza,
                R.drawable.custompizza
        };

        StringBuilder name = new StringBuilder();
        int image = chooseSpecialtyImage(position, name);
        Pizza pizza = PizzaMaker.createPizza(name.toString());
        holder.pizzaImage.setImageResource(images[image]);
        ArrayList<Topping> list = pizza.getToppings();
        ArrayAdapter<Topping> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list);
        holder.toppingsList.setAdapter(adapter);
    }

    private int chooseSpecialtyImage(int position, StringBuilder name) {
        String image = itemsList.get(position).getImage();
        switch (image) {
            case "deluxe":
                name.append("deluxe");
                return 0;
            case "supreme":
                name.append("supreme");
                return 1;
            case "meatzza":
                name.append("meatzza");
                return 2;
            case "seafood":
                name.append("seafood");
                return 3;
            case "pepperoni":
                name.append("pepperoni");
                return 4;
            case "cheeselovers":
                name.append("cheeselovers");
                return 5;
            case "grilledchicken":
                name.append("grilledchicken");
                return 6;
            case "chickenalfredo":
                name.append("chickenalfredo");
                return 7;
            case "hawaiian":
                name.append("hawaiian");
                return 8;
            case "gardenfresh":
                name.append("gardenfresh");
                return 9;
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
