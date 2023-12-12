package com.example.rupizza;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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
        int i = 1;
        for (Order order : storeOrders.getOrders()) {
            arraySpinner.add("" + i);
            i++;
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);
    }

    private void sendResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Store Orders", storeOrders);
        //System.out.println("On send result: " + storeOrders.getNextAvailableOrderNum());
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void selectOrder(View view){
        String text = spinner.getSelectedItem().toString();
        ArrayList<Order> list = (ArrayList<Order>) storeOrders.getOrders();
        for (Order order : list){
            if (order.getOrderNumber() == Integer.parseInt(text)) shoppingCart = order;
        }
        ArrayAdapter<Pizza> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingCart.getPizzas());
        pizzaList.setAdapter(adapter);
    }

    public void handleCancelOrder(View view) {
        return;
        /*try {
            int selectedOrder = Integer.parseInt(orderNumber.getSelectionModel().getSelectedItem());
            int comboNum = orderNumber.getSelectionModel().getSelectedIndex();
            for (Order order : storeOrders.getOrders()) {
                if (selectedOrder == order.getOrderNumber()) {
                    storeOrders.cancelOrder(order);
                }
            }

            orderTotal.clear();
            orderNumber.getItems().remove(comboNum);
            pizzaList.getItems().clear();
            if (!orderNumber.getSelectionModel().isEmpty()) {
                selectedOrder = Integer.parseInt(orderNumber.getSelectionModel().getSelectedItem());
                for (Order order : storeOrders.getOrders()) {
                    if (selectedOrder == order.getOrderNumber()) {
                        for (Pizza pizza : order.getPizzas()) {
                            pizzaList.getItems().add(pizza.toString());
                        }
                    }
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Order Cancelled");
            alert.setHeaderText("The selected order has been canceled.");
            alert.showAndWait();
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Cancellation Error");
            alert.setHeaderText("Could not cancel selected order, if any.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }*/
    }
}