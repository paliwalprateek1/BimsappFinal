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
        checkPreviousUser();

    }

    public void continueNumberLocation(View view) {
        if(etMobileNumber.getText().toString().length()!=10){
            Toast.makeText(NumberLocation.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
        }else {
            storeSharedPreferences.setUserNumber(getApplicationContext(), etMobileNumber.getText().toString());
            if(checkPreviousUser()){
                waitTime();
            }else{
                createUser();
            }

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

    public boolean checkPreviousUser(){

        return false;
    }

    public void createUser(){
        User user = new User();
        user.setEmail(storeSharedPreferences.getUserEmail(this));
        user.setName(storeSharedPreferences.getUserName(this));
        user.setNumber(etMobileNumber.getText().toString());
        user.setPoints("100");

        Firebase newRef = ref.child("User").push();
        newRef.setValue(user);

        waitTime();

    }
}

class User{
    private String name;
    private String number;
    private String email;

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

    private String points;
}
