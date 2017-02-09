package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

public class OrderFoodFinal extends AppCompatActivity {

    private String address, coordinates, remarks;
    TextView nameTv, addressTv, totalTv, remarksTv, foodItemList;
    Order order = new Order();
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    List<FoodQuantity> l = new ArrayList<>();
    String itemString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);

        l = storeSharedPreferences.loadFavorites(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        address = extras.getString("address");
        coordinates = extras.getString("coordinates");
        remarks = extras.getString("remarks");

        order.setAddress(address);
        order.setAmount("thats");
        order.setCoordinates(coordinates);

        for (int i=0;i<l.size();i++){
            itemString = itemString + l.get(i).getFood() +"          "+
                    l.get(i).getQuantity() +"          "+ l.get(i).getPrice() +","+"\n";
        }

        foodItemList = (TextView)findViewById(R.id.foodItemList);
        foodItemList.setText(itemString);
        order.setItem(itemString);


        order.setName(storeSharedPreferences.getUserName(this));
        order.setMail(storeSharedPreferences.getUserEmail(this));
        order.setNumber(storeSharedPreferences.getUserNumber(this));
        Toast.makeText(this, storeSharedPreferences.getUserNumber(this), Toast.LENGTH_SHORT).show();

        nameTv = (TextView)findViewById(R.id.nameTv);
        nameTv.setText(storeSharedPreferences.getUserName(this));

        addressTv = (TextView)findViewById(R.id.addressTv);
        addressTv.setText(address);

        totalTv = (TextView) findViewById(R.id.totalTv);
        totalTv.setText("thats");

        remarksTv = (TextView) findViewById(R.id.remarksTv);
        remarksTv.setText(remarks);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Kutte order kyon cancel kiya", Toast.LENGTH_SHORT).show();
    }

    public void order(View view) {
        Firebase.setAndroidContext(getApplicationContext());
        ref = new Firebase(Server.URL);
        Firebase newRef = ref.child("Order").push();
        newRef.setValue(order);
    }

    public void cancelOrder(View view) {
        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
    }
}
