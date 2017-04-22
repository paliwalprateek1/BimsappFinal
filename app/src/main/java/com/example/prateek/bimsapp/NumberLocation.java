package com.example.prateek.bimsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NumberLocation extends AppCompatActivity {

    EditText etMobileNumber;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_location);
        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);


        etMobileNumber = (EditText)findViewById(R.id.etMobileNumber);

    }

    public void continueNumberLocation(View view) {
        if(etMobileNumber.getText().toString().length()!=10){
            Toast.makeText(NumberLocation.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
        }else {
            checkPreviousUser(storeSharedPreferences.getUserEmail(this));
            storeSharedPreferences.setUserNumber(getApplicationContext(), etMobileNumber.getText().toString());
        }

    }

    public void waitTime(){
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), SelectRestraunt.class);
                        startActivity(intent);
                        finish();
                    }
                },
                2000
        );
    }

    public void checkPreviousUser(String email){
        Firebase objRef = ref.child("User");
        Query pendingTasks = objRef.orderByChild("email").equalTo(email);
        pendingTasks.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot tasksSnapshot) {
                if (tasksSnapshot.exists()){
                    Toast.makeText(getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
                   // getUserData();
                    waitTime();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Registration Succesfull", Toast.LENGTH_SHORT).show();
                    createUser();
                }
//                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
//
//                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void getUserData(){

    }

    public void createUser(){
        User user = new User();
        user.setEmail(storeSharedPreferences.getUserEmail(this));
        user.setName(storeSharedPreferences.getUserName(this));
        user.setNumber(etMobileNumber.getText().toString());
        user.setPoints("0");
        user.setNumberOfOrders("0");

        Firebase newRef = ref.child("User").push();
        newRef.setValue(user);

        waitTime();

    }
}

class User{
    private String name;
    private String number;
    private String email;
    private String points;
    private String numberOfOrders;


    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(String numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
