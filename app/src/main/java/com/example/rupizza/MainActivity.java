package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

    protected void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println("request code is " + requestCode + "result code is " + resultCode);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                if(bundle.getSerializable("Shopping Cart") != null) shoppingCart = (Order) bundle.getSerializable("Shopping Cart");
                if(bundle.getSerializable("Store Orders") != null) storeOrders = (StoreOrders) bundle.getSerializable("Store Orders");
                if (shoppingCart.getOrderNumber() < storeOrders.getNextAvailableOrderNum()) clearCart();
                //System.out.println("On activity result: " + storeOrders.getNextAvailableOrderNum());
            }
        }
    }

    private void clearCart(){
        for (Pizza pizza : shoppingCart.getPizzas()) {
            shoppingCart.removePizza(pizza);
        }
        shoppingCart.setOrderNumber(storeOrders.getNextAvailableOrderNum());
        shoppingCart.setOrderPlaced(false);
    }

    public void openSpecialties (View view){
        Intent intent = new Intent(MainActivity.this, SpecialtiesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        //System.out.println("On openSpecialties: " + shoppingCart.getPizzas());
        startActivityForResult(intent, 1);
    }

    public void openCustoms (View view){
        Intent intent = new Intent(MainActivity.this, CustomsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }

    public void openCart (View view){
        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        //System.out.println("On openCart: " + shoppingCart.getPizzas());
        startActivityForResult(intent, 1);
    }

    public void openOrders (View view){
        Intent intent = new Intent(MainActivity.this, OrdersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Shopping Cart", shoppingCart);
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }
}