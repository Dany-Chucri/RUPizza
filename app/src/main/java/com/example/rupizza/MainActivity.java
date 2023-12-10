package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSpecialties(View view) {
        startActivity(new Intent(MainActivity.this, SpecialtiesActivity.class));
    }

    public void openCustoms(View view) {
        startActivity(new Intent(MainActivity.this, CustomsActivity.class));
    }

    public void openCart(View view) {
        startActivity(new Intent(MainActivity.this, CartActivity.class));
    }

    public void openOrders(View view) {
        startActivity(new Intent(MainActivity.this, OrdersActivity.class));
    }
}