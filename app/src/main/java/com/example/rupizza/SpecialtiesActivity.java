package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;


public class SpecialtiesActivity extends AppCompatActivity {
    protected Order shoppingCart;
    protected StoreOrders storeOrders;
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    private RadioGroup radioGroup;
    private Switch extraSauce, extraCheese;
    private EditText quantity;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        shoppingCart = (Order) bundle.getSerializable("Shopping Cart");
        storeOrders = (StoreOrders) bundle.getSerializable("Store Orders");
        if (shoppingCart.getOrderPlaced()) clearCart();
        //System.out.println("On specialties onCreate: " + shoppingCart.getPizzas());

        setContentView(R.layout.activity_specialties_view);
        recyclerView = findViewById(R.id.recyclerView);
        itemsList = new ArrayList<>();

        setSpecialtyPizzas();
        setAdapter();
        radioGroup = findViewById(R.id.radioGroup);
        extraCheese = findViewById(R.id.switch3);
        extraSauce = findViewById(R.id.switch4);
        quantity = findViewById(R.id.editTextNumber);
        radioButton = findViewById(R.id.radioButton8);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Toast.makeText(getApplicationContext(), "Hello" + position, Toast.LENGTH_SHORT).show();
                        chooseSpecs(view, position);
                    }
                })
        );
    }

    private void sendResult() {
        //System.out.println("On sendResult: " + shoppingCart.getPizzas());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(itemsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setSpecialtyPizzas() {
        itemsList.add(new Item("deluxe", PizzaMaker.createPizza("deluxe").toppings));
        itemsList.add(new Item("supreme", PizzaMaker.createPizza("supreme").toppings));
        itemsList.add(new Item("meatzza", PizzaMaker.createPizza("meatzza").toppings));
        itemsList.add(new Item("seafood", PizzaMaker.createPizza("seafood").toppings));
        itemsList.add(new Item("pepperoni", PizzaMaker.createPizza("pepperoni").toppings));
        itemsList.add(new Item("cheeselovers", PizzaMaker.createPizza("cheeselovers").toppings));
        itemsList.add(new Item("grilledchicken", PizzaMaker.createPizza("grilledchicken").toppings));
        itemsList.add(new Item("gardenfresh", PizzaMaker.createPizza("gardenfresh").toppings));
        itemsList.add(new Item("chickenalfredo", PizzaMaker.createPizza("chickenalfredo").toppings));
        itemsList.add(new Item("hawaiian", PizzaMaker.createPizza("hawaiian").toppings));
    }

    public void chooseSpecs(View  view, int position) {
        if (checkErrors() != 0) return;
        StringBuilder name = new StringBuilder();
        chooseSpecialtyImage(position, name);
        Pizza pizza = PizzaMaker.createPizza(name.toString());
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        pizza.setSize(radioButton.getText().toString());
        pizza.setExtraCheese(extraCheese.isChecked());
        pizza.setExtraSauce(extraSauce.isChecked());
        double price = pizza.price() * Integer.parseInt(String.valueOf(quantity.getText()));
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Total Cost: " + NumberFormat.getCurrencyInstance().format(price) + "\nWould you like to add this pizza to your order?").setTitle("Pizza Creator");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                shoppingCart.addPizza(pizza);
                for (int i = Integer.parseInt(String.valueOf(quantity.getText())); i > 1; i--) {
                    Pizza pizzacopy = PizzaMaker.createPizza(pizza.getPizzaType());
                    pizzacopy.copyPizza(pizza);
                    shoppingCart.addPizza(pizzacopy);
                }
                Toast.makeText(getApplicationContext(), "Pizza has been added to cart", Toast.LENGTH_LONG).show();
                sendResult();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Pizza was not added to cart.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private int checkErrors() {
        int error = 0;
        if (radioGroup.getCheckedRadioButtonId() == -1) error = -1;
        try{
            if (Integer.parseInt(String.valueOf(quantity.getText())) <= 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Specialty Pizza Error");
                alert.setMessage("Please enter a valid quantity.");
                alert.setPositiveButton("OK", null);
                alert.show();
                error = -2;
            }
        }
        catch(Exception e){
            error = -1;
        }
        if (error == -1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Specialty Pizza Error");
            alert.setMessage("Could not select pizza.\nDo not leave size or quantity blank.");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        return error;
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

    private void clearCart(){
        for (Pizza pizza : shoppingCart.getPizzas()) {
            shoppingCart.removePizza(pizza);
        }
        shoppingCart.setOrderNumber(storeOrders.getNextAvailableOrderNum());
        shoppingCart.setOrderPlaced(false);
    }
}