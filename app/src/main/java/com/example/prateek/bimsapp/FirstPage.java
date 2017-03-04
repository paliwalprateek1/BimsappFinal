package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FirstPage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 8000;

    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //this one is the real shit
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Toast.makeText(this, "10", Toast.LENGTH_SHORT).show();



        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);


        storeSharedPreferences.removeAllQuant(this);



   //     if(isNetworkAvailable()){
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    if(StoreSharedPreferences.getUserEmail(getApplicationContext())!=null) {
                        Intent i = new Intent(FirstPage.this, MenuMain.class);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(FirstPage.this, Login.class);
                        startActivity(i);
                    }

                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);

        //getAllMenu();


//        }
//        else{
//            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
//        }

    }

    public void getAllMenu() {
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("feature");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());
                    food.setAvailability(null);
                    food.setRating(null);
                    food.setCat("feature");
                    storeSharedPreferences.addFavoriteData(FirstPage.this, food);
                    //foodListAll.add(food);
                    //mAdapter.notifyDataSetChanged();
                    Log.d("food " + value.toString(), "price " + valueF.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        getVeg();

    }

    public void getVeg() {
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("veg");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());
                    food.setAvailability(null);
                    food.setRating(null);
                    food.setCat("veg");
                    storeSharedPreferences.addFavoriteData(FirstPage.this, food);
//foodListAll.add(food);
                    //mAdapter.notifyDataSetChanged();
                    Log.d("food " + value.toString(), "price " + valueF.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        getNonVeg();
    }

    public void getNonVeg() {
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("nonveg");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());
                    food.setAvailability(null);
                    food.setRating(null);
                    food.setCat("nonveg");
//                    foodListAll.add(food);
//                    mAdapter.notifyDataSetChanged();
                    storeSharedPreferences.addFavoriteData(FirstPage.this, food);
                    Log.d("food " + value.toString(), "price " + valueF.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
