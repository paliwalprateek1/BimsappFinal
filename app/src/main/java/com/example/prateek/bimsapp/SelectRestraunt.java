package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

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
        //midnight = (LinearLayout)findViewById(R.id.midnightScorer);

        bims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeSharedPreferences.setKitchenDatabase(getApplicationContext(), "email");
                storeSharedPreferences.setKitchenName(getApplicationContext(), "Bims' Kitchen");
                Toast.makeText(getApplicationContext(), "Bims' Kitchen", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SelectRestraunt.this, QQ.class));

            }
        });
//        midnight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                storeSharedPreferences.setKitchenDatabase(getApplicationContext(), "kuch bhi");
//                storeSharedPreferences.setKitchenName(getApplicationContext(), "Midnight Scorer");
//                Toast.makeText(getApplicationContext(), "Midnight", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(SelectRestraunt.this, QQ.class);
//                startActivity(i);
//            }
//        });
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
        storeSharedPreferences.setUserCoordinatesLatitudes(this, null);
        storeSharedPreferences.setUserEmail(this, null);
        storeSharedPreferences.setUserRemarks(this, null);
        storeSharedPreferences.setUserCustomLocation(this, null);
        //also remove the shared preferences
    }

    public void credits(View view) {
        startActivity(new Intent(this, Credits.class));
    }


}
