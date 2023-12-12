package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.text.NumberFormat;

public class CartActivity extends AppCompatActivity {
    protected Order shoppingCart;
    protected StoreOrders storeOrders;
    private static int position;

    private ListView cartList;
    private EditText orderNumber, subtotal, salesTax, orderTotal;
    private Button removePizza, placeOrder;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        shoppingCart = (Order) bundle.getSerializable("Shopping Cart");
        storeOrders = (StoreOrders) bundle.getSerializable("Store Orders");

        System.out.println("On cart onCreate: " + shoppingCart.getPizzas());

        cartList = findViewById(R.id.cartList);
        orderNumber = findViewById(R.id.textView11);
        subtotal = findViewById(R.id.textView13);
        salesTax = findViewById(R.id.textView15);
        removePizza = findViewById(R.id.button1);
        placeOrder = findViewById(R.id.button2);

        ArrayAdapter<Pizza> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCart.getPizzas());
        cartList.setAdapter(adapter);
        cartList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CartActivity.position = position;
            }
        });
        orderNumber.setText("" + shoppingCart.getOrderNumber());
    }

    public void addPizza(Pizza pizza) {
        shoppingCart.addPizza(pizza);
        updatePrices();
    }

    public void removePizza(Pizza pizza) {
        shoppingCart.removePizza(pizza);
        updatePrices();
    }

    public void updatePrices() {
        double total = 0;
        for (Pizza pizza : shoppingCart.getPizzas()){
            total += pizza.price();
        }
        double taxAmount = (total * Pizza.SALES_TAX) - total;
        double totalPrice = total+ taxAmount;

        subtotal.setText(NumberFormat.getCurrencyInstance().format(total));
        salesTax.setText(NumberFormat.getCurrencyInstance().format(taxAmount));
        orderTotal.setText(NumberFormat.getCurrencyInstance().format(totalPrice));
    }

    private void handleRemovePizza() {
        try {
            Pizza selectedPizza = (Pizza) cartList.getAdapter().getItem(position);
            removePizza(selectedPizza);
        }
        catch (Exception e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Removal Error");
            alert.setMessage("Could not remove selected pizza.\nPlease try again.");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    private void handlePlaceOrder() {
        try {
            Order newOrder = new Order();
            newOrder.copyOrder(shoppingCart);
            //TODO : adding the order itself to the store orders, i.e., storeOrdersController.addCartOrder(newOrder);

            shoppingCart.setOrderNumber(storeOrders.getNextAvailableOrderNum());

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Order Info");
            alert.setMessage("Order Placed!\nThe store has received your order.");
            alert.show();
            clearCart();

        }
        catch (Exception e){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Purchase error");
            alert.setMessage("Could not place order.\nPlease try again after checking your information.");
            alert.show();
        }
    }

    private void clearCart(){
        for (Pizza pizza : shoppingCart.getPizzas()) {
            shoppingCart.removePizza(pizza);
        }
        updatePrices();
        orderNumber.setText(shoppingCart.getOrderNumber());
    }
}