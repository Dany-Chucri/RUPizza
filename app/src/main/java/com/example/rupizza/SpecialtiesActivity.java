package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    public void chooseSpecs(View  view, int position) {
       StringBuilder name = new StringBuilder();
        int image = chooseSpecialtyImage(position, name);
        Pizza pizza = PizzaMaker.createPizza(name.toString());
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        pizza.setSize(radioButton.getText().toString());
        pizza.setExtraCheese(extraCheese.isChecked());
        pizza.setExtraSauce(extraSauce.isChecked());
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Total Cost: " + NumberFormat.getCurrencyInstance().format(pizza.price()) + "\nWould you like to add this pizza to your order?").setTitle("Pizza Creator");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
                //Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //TODO
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
        }
        return -1;
    }

//    private Pizza buildSpecialty(View view) {
//        String selected = itemsList.get();
//        setSpecialtyImage(selected);
//        Pizza pizza = PizzaMaker.createPizza(selected);
//        RadioButton selectedRadioButton = (RadioButton) specialtySize.getSelectedToggle();
//        pizza.setSize(selectedRadioButton.getText());
//        pizza.setExtraSauce(extraSauce.isSelected());
//        pizza.setExtraCheese(extraCheese.isSelected());
//
//        totalPrice.clear();
//        sauce.clear();
//        toppings.setItems(FXCollections.observableList((List) pizza.toppings));
//        NumberFormat.getCurrencyInstance().format(pizza.price());
//        totalPrice.appendText(NumberFormat.getCurrencyInstance().format(pizza.price()));
//        sauce.appendText(pizza.sauce.toString());
//
//        return pizza;
//    }
}