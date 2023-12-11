package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class SpecialtiesActivity extends AppCompatActivity {
    private ArrayList<Item> itemsList;
    private RecyclerView recyclerView;

    private RadioGroup radioGroup;
    private Switch extraSauce, extraCheese;
    private EditText quantity;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    public void onItemClick(View v, int position) {

                        Toast.makeText(getApplicationContext(), "Hello" + position, Toast.LENGTH_SHORT).show();
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

    public void chooseSpecs(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        //NumberFormat.getCurrencyInstance().format(pizza.price());
        double sampleCost = 100.00;
        alert.setMessage("Total Cost: " + sampleCost + "\nWould you like to add this pizza to your order?").setTitle("Pizza Creator");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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