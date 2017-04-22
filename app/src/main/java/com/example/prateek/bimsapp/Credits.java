package com.example.prateek.bimsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Credits");


        TextView tv = (TextView) findViewById(R.id.icons8);
        tv.setText(Html.fromHtml("<a href=http://www.icons8.com> Icons8 "));
        tv.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tv2 = (TextView) findViewById(R.id.UIUX);
        tv2.setText(Html.fromHtml("<a href=http://ansarimofid.in> Mofid Ansari "));
        tv2.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
