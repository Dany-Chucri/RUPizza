package com.example.rupizza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
    protected Order shoppingCart;
    protected StoreOrders storeOrders;

    private ListView pizzaList;
    private EditText orderTotal;
    private Button cancelOrder;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        pizzaList = findViewById(R.id.pizzaList);
        orderTotal = findViewById(R.id.textView20);
        cancelOrder = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        storeOrders = (StoreOrders) bundle.getSerializable("Store Orders");

        ArrayList<String> arraySpinner= new ArrayList<String>();
        for (int i = 0; i < storeOrders.getOrders().size(); i++) {
            arraySpinner.add("" + storeOrders.getOrders().get(i).getOrderNumber());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
        final int[] selectedPosition = {0};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
                selectedPosition[0] = position;
                selectOrder();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
            }
        });
    }

    private void sendResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Store Orders", storeOrders);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void selectOrder(){
        int i = Integer.parseInt(spinner.getSelectedItem().toString());
        ArrayList<Order> list = (ArrayList<Order>) storeOrders.getOrders();
        for (Order order : list){
            if (order.getOrderNumber() == i) {
                shoppingCart = order;
                break;
            }
        }
        ArrayAdapter<Pizza> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCart.getPizzas());
        pizzaList.setAdapter(adapter);
        updatePrices();
    }

    public void updatePrices() {
        double total = 0;
        for (Pizza pizza : shoppingCart.getPizzas()){
            total += pizza.price();
        }
        double taxAmount = (total * Pizza.SALES_TAX) - total;
        double totalPrice = total+ taxAmount;
        orderTotal.setText(NumberFormat.getCurrencyInstance().format(totalPrice));
    }

    public void handleCancelOrder(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Are you sure you would like to cancel this order?").setTitle("Cancel Confirmation");
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cancelHelper();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Order was not canceled.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    private void cancelHelper() {
        try {
            int i = Integer.parseInt(spinner.getSelectedItem().toString());
            Order toBeCanceled = null;
            for (Order order : storeOrders.getOrders()){
                if (order.getOrderNumber() == i)
                {
                    toBeCanceled = order;
                    break;
                }
            }
            if (toBeCanceled != null) storeOrders.cancelOrder(toBeCanceled);
            updatePrices();
            Toast.makeText(getApplicationContext(), "Order was canceled.", Toast.LENGTH_LONG).show();
            sendResult();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Could not cancel order.", Toast.LENGTH_LONG).show();
        }
    }
}