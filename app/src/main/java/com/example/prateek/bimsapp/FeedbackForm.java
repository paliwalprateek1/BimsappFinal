package com.example.prateek.bimsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class FeedbackForm extends AppCompatActivity {

    Toolbar toolbar;
    EditText feedbackEditText;
    Button feedbackButton;
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_form);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback Form");


                feedbackEditText = (EditText)findViewById(R.id.feedbackEditText);

        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);

        feedbackButton = (Button)findViewById(R.id.feedbackButton);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });
    }

    public void sendFeedback(){
        //send feedback firebase logic
        Feedback feedback = new Feedback();
        feedback.setEmail(storeSharedPreferences.getUserEmail(this));
        feedback.setName(storeSharedPreferences.getUserName(this));
        feedback.setNumber(storeSharedPreferences.getUserNumber(this));
        feedback.setFeedback(feedbackEditText.getText().toString());
        Firebase newRef = ref.child("Feedback").push();
        newRef.setValue(feedback);
        feedbackEditText.setText(null);
        Toast.makeText(this, "Feedback submitted", Toast.LENGTH_SHORT).show();
        finish();
    }
}

class Feedback{
    String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    String number;
    String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    String feedback;
}
