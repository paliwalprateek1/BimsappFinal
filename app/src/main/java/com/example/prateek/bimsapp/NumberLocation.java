package com.example.prateek.bimsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NumberLocation extends AppCompatActivity {

    TextView tvSelectLocation;
    EditText etMobileNumber;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_location);

        tvSelectLocation = (TextView)findViewById(R.id.tvSelectLocation);
        etMobileNumber = (EditText)findViewById(R.id.etMobileNumber);
    }

    public void continueNumberLocation(View view) {

        storeSharedPreferences.setUserNumber(this, etMobileNumber.getText().toString());

        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
    }
}
