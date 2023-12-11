package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    protected Order shoppingCart;
    protected StoreOrders storeOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoppingCart = new Order();
        storeOrders = new StoreOrders();
        setContentView(R.layout.activity_main);
    }

    public void openSpecialties(View view) {
        Intent intent = new Intent(MainActivity.this, SpecialtiesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openCustoms(View view) {
        Intent intent = new Intent(MainActivity.this, CustomsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openCart(View view) {
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openOrders(View view) {
        Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}