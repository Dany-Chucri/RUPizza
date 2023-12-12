package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

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

    private ArrayAdapter<String> sizeAdapter, additionalToppingsAdapter, selectedToppingsAdapter;
    private ArrayList<Item> itemsList;
    private ListView additionalToppings,selectedToppings;
    private Pizza pizza;
    private Spinner sizeChooser;
    private EditText totalPrice;
    private CheckBox extraSauce, extraCheese;
    private RadioButton tomatoSauce, alfredoSauce;
    private TextView selectedToppingsLabel,additionalToppingsLabel;
    private Button addTopping, removeTopping, addToOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customs_view);
//        itemsList = new ArrayList<>();
        initializeUI();
        setupSpinner();
        setupSelectedToppingsListView();
    }

    private void initializeUI() {
        sizeChooser = findViewById(R.id.sizeChooser);
        totalPrice = findViewById(R.id.totalPrice);
        extraSauce = findViewById(R.id.extraSauce);
        extraCheese = findViewById(R.id.extraCheese);
        tomatoSauce = findViewById(R.id.tomatoSauce);
        alfredoSauce = findViewById(R.id.alfredoSauce);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        sizeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.size_array));
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeChooser.setAdapter(sizeAdapter);
//        additionalToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.additional_toppings_array));
        selectedToppingsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    }

    private void setupSpinner() {
        sizeChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectSize();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void selectSize() {
        String selectedSize = sizeChooser.getSelectedItem().toString();
        switch (selectedSize) {
            case "Small":
                Toast.makeText(CustomsActivity.this, "Selected Small size", Toast.LENGTH_SHORT).show();
                break;
            case "Medium":
                Toast.makeText(CustomsActivity.this, "Selected Medium size", Toast.LENGTH_SHORT).show();
                break;
            case "Large":
                Toast.makeText(CustomsActivity.this, "Selected Large size", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void setupSelectedToppingsListView() {
        selectedToppings = findViewById(R.id.selectedToppings);

        ArrayList<String> selectedToppingsList = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedToppingsList);

        selectedToppings.setAdapter(adapter);

        selectedToppings.setOnItemClickListener((parent, view, position, id) -> {
            selectedToppingsList.remove(position);
            adapter.notifyDataSetChanged();
        });
    }
    private void setupButtons() {
        addTopping = findViewById(R.id.addTopping);
        removeTopping = findViewById(R.id.removeTopping);
        addToOrder = findViewById(R.id.addToOrder);
        addTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomsActivity.this, "Add Topping button clicked", Toast.LENGTH_SHORT).show();
                if (additionalToppings.getAdapter().isEmpty() || additionalToppings.getAdapter().getCount() == Pizza.MAX_TOPPINGS - 1)
                    return;
                String selected = (String) additionalToppings.getItemAtPosition(additionalToppings.getSelectedItemPosition());
                pizza.addTopping(selected);

                ArrayAdapter<String> additionalAdapter = (ArrayAdapter<String>) additionalToppings.getAdapter();
                additionalAdapter.remove(selected);

                ArrayAdapter<String> selectedAdapter = (ArrayAdapter<String>) selectedToppings.getAdapter();
                selectedAdapter.add(selected);

                totalPrice.setText(NumberFormat.getCurrencyInstance().format(pizza.price()));
            }
        });
        removeTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CustomsActivity.this, "Remove Topping button clicked", Toast.LENGTH_SHORT).show();
                if (pizza.getToppings().isEmpty()) return;
                String selected = (String) selectedToppings.getSelectedItem();
                pizza.removeTopping(selected);
                selectedToppingsAdapter.remove(selected);
                additionalToppingsAdapter.add(selected);
                totalPrice.setText(NumberFormat.getCurrencyInstance().format(pizza.price()));
            }
        });
        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pizza customPizza = PizzaMaker.createPizza("buildyourown");
                customPizza.copyPizza(pizza);
//                CartActivity.addPizza(pizza);
                Toast.makeText(CustomsActivity.this, "Add to Order button clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setExtra() {
        pizza.setExtraSauce(extraSauce.isChecked());
        pizza.setExtraCheese(extraCheese.isChecked());
        double updatedPrice = pizza.price();
        totalPrice.setText(NumberFormat.getCurrencyInstance().format(updatedPrice));
    }
    public void selectAlfredo() {
        pizza.setSauce("alfredo");
        double updatedPrice = pizza.price();
        totalPrice.setText(NumberFormat.getCurrencyInstance().format(updatedPrice));
    }
}
