package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CustomsActivity extends AppCompatActivity {
    protected Order shoppingCart;
    protected StoreOrders storeOrders;
    private static int positionAdd, positionRemove;
    private ArrayList<Topping> additionalToppingsList, selectedToppingsList;
    private Size size;

    private ListView additionalToppings, selectedToppings;
    private Spinner sizeChooser;
    private EditText totalPrice;
    private CheckBox extraSauce, extraCheese;
    private RadioGroup radioGroup2;
    private RadioButton selectedButton;
    private Button addTopping, removeTopping, addToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customs_view);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        shoppingCart = (Order) bundle.getSerializable("Shopping Cart");
        storeOrders = (StoreOrders) bundle.getSerializable("Store Orders");
        if (shoppingCart.getOrderPlaced()) clearCart();
        initializeUI();
        setupSpinner();
        setUpToppingsLists();
    }

    private void initializeUI() {
        sizeChooser = findViewById(R.id.sizeChooser);
        totalPrice = findViewById(R.id.totalPrice);
        extraSauce = findViewById(R.id.extraSauce);
        extraCheese = findViewById(R.id.extraCheese);
        radioGroup2 = findViewById(R.id.radioGroup2);
    }

    private void setUpToppingsLists() {
        createToppingsLists();

        additionalToppings = findViewById(R.id.additionalToppings);
        ArrayAdapter<Topping> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, additionalToppingsList);
        additionalToppings.setAdapter(adapter);
        additionalToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomsActivity.positionAdd = position;
            }
        });

        selectedToppings = findViewById(R.id.selectedToppings);
        ArrayAdapter<Topping> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppingsList);
        selectedToppings.setAdapter(adapter2);
        selectedToppings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomsActivity.positionRemove = position;
            }
        });
    }

    private void createToppingsLists() {
        additionalToppingsList = new ArrayList<>();
        additionalToppingsList.add(Topping.SAUSAGE);
        additionalToppingsList.add(Topping.CHICKEN);
        additionalToppingsList.add(Topping.BEEF);
        additionalToppingsList.add(Topping.HAM);
        additionalToppingsList.add(Topping.PEPPERONI);
        additionalToppingsList.add(Topping.SHRIMP);
        additionalToppingsList.add(Topping.SQUID);
        additionalToppingsList.add(Topping.CRAB_MEATS);
        additionalToppingsList.add(Topping.MUSHROOM);
        additionalToppingsList.add(Topping.GREEN_PEPPER);
        additionalToppingsList.add(Topping.PINEAPPLE);
        additionalToppingsList.add(Topping.BLACK_OLIVE);
        additionalToppingsList.add(Topping.ONION);

        selectedToppingsList = new ArrayList<>();
    }

    private void setupSpinner() {
        ArrayList<String> arraySpinner= new ArrayList<String>();
        arraySpinner.add("SMALL");
        arraySpinner.add("MEDIUM");
        arraySpinner.add("LARGE");
        sizeChooser = findViewById(R.id.sizeChooser);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeChooser.setAdapter(adapter);
        sizeChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                size = Size.valueOf(sizeChooser.getSelectedItem().toString());
                updatePrice();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                size = Size.SMALL;
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void clearCart(){
        for (Pizza pizza : shoppingCart.getPizzas()) {
            shoppingCart.removePizza(pizza);
        }
        shoppingCart.setOrderNumber(storeOrders.getNextAvailableOrderNum());
        shoppingCart.setOrderPlaced(false);
    }

    public void selectTopping(View view) {
        if (selectedToppingsList.size() >= 7){
            Toast.makeText(getApplicationContext(), "You cannot add more than 7 toppings", Toast.LENGTH_LONG).show();
            return;
        }
        Topping topping = (Topping) additionalToppings.getAdapter().getItem(positionAdd);
        selectedToppingsList.add(topping);
        additionalToppingsList.remove(topping);

        ArrayAdapter<Topping> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, additionalToppingsList);
        additionalToppings.setAdapter(adapter);
        ArrayAdapter<Topping> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppingsList);
        selectedToppings.setAdapter(adapter2);
        updatePrice();
    }

    public void deselectTopping(View view) {
        if (selectedToppingsList.size() <= 3){
            Toast.makeText(getApplicationContext(), "You cannot have less than 7 toppings", Toast.LENGTH_LONG).show();
            return;
        }
        Topping topping = (Topping) selectedToppings.getAdapter().getItem(positionRemove);
        additionalToppingsList.add(topping);
        selectedToppingsList.remove(topping);

        ArrayAdapter<Topping> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, additionalToppingsList);
        additionalToppings.setAdapter(adapter);
        ArrayAdapter<Topping> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppingsList);
        selectedToppings.setAdapter(adapter2);
        updatePrice();
    }

    private void updatePrice() {
        try {
            Pizza pizza = setUpPizza();
            totalPrice.setText(NumberFormat.getCurrencyInstance().format(pizza.price()));
        }
        catch (Exception e) {
            return;
        }
    }

    public void checkSauce(View view) {
        updatePrice();
    }

    private Pizza setUpPizza() {
        int selectedId = radioGroup2.getCheckedRadioButtonId();
        selectedButton = findViewById(selectedId);
        Pizza pizza = PizzaMaker.createPizza("buildyourown");
        pizza.setExtraCheese(extraCheese.isChecked());
        pizza.setExtraSauce(extraSauce.isChecked());
        pizza.setSize(size.toString());
        int index = selectedButton.getText().toString().indexOf(' ');
        String sauce = selectedButton.getText().toString().substring(0, index);
        pizza.setSauce(sauce);
        for (Topping topping : selectedToppingsList) {
            pizza.addTopping(topping.toString());
        }
        return pizza;
    }

    public void addToOrder(View view) {
        if (checkErrors() != 0) return;
        Pizza pizza = setUpPizza();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Total Cost: " + NumberFormat.getCurrencyInstance().format(pizza.price()) + "\nWould you like to add this pizza to your order?").setTitle("Pizza Creator");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                shoppingCart.addPizza(pizza);
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
        if (radioGroup2.getCheckedRadioButtonId() == -1) error = -1;
        try{
            if (selectedToppingsList.size() < 3 || selectedToppingsList.size() > 7) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Custom Pizza Error");
                alert.setMessage("Make sure you have between 3 to 7 toppings selected.");
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
            alert.setTitle("Custom Pizza Error");
            alert.setMessage("Could not select pizza.\nMake sure you have a size and sauce selected.");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
        return error;
    }
}
