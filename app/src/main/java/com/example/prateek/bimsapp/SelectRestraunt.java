package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.vision.text.Line;

public class SelectRestraunt extends AppCompatActivity {
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    LinearLayout bims, midnight;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_restraunt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);

        bims = (LinearLayout)findViewById(R.id.bimsKitchen);
        midnight = (LinearLayout)findViewById(R.id.midnightScorer);

        bims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setKitchenDatabase(getApplicationContext(), "email");
                storeSharedPreferences.setKitchenName(getApplicationContext(), "Bims' Kitchen");
                Toast.makeText(getApplicationContext(), "bims", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectRestraunt.this, MenuMain.class);
                startActivity(i);
            }
        });
        midnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setKitchenDatabase(getApplicationContext(), "kuch bhi");
                storeSharedPreferences.setKitchenName(getApplicationContext(), "Midnight Scorer");
                Toast.makeText(getApplicationContext(), "Midnight", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectRestraunt.this, MenuMain.class);
                startActivity(i);
            }
        });
    }

    public void goToRestraunt(View view) {
        sendNotification();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_restraunt_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.changeNumber:
                changeNumber();
                return true;
            case R.id.signOut:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeNumber(){

    }

    public void signOut(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
        //also remove the shared preferences
    }


    public void sendNotification(){
        String kitchenToken = getToken();




    }

    String token;
    public String getToken(){
        Firebase ref = new Firebase(Server.URL+"/KitchenToken");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                token = value;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return token;
    }
}
