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

public class CustomsActivity extends AppCompatActivity {
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

        setContentView(R.layout.activity_specialties_view);
        recyclerView = findViewById(R.id.recyclerView);
        itemsList = new ArrayList<>();

//        setCustomsPizzas();
//
//        ordersAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Order order) {
//            }
//        });
    }

}