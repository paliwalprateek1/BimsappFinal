package com.example.prateek.bimsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        TextView raj = (TextView)findViewById(R.id.adminDEvtv);
        raj.setText(Html.fromHtml("<a href=https://github.com/rajeeviiit> Rajeev Singh"));
        raj.setMovementMethod(LinkMovementMethod.getInstance());


        TextView raviPatel = (TextView) findViewById(R.id.ravipatel);
        raviPatel.setText(Html.fromHtml("<a href=https://github.com/ravi268kumar> Ravi Patel"));
        raviPatel.setMovementMethod(LinkMovementMethod.getInstance());


        TextView ravi = (TextView)findViewById(R.id.ravi);
        ravi.setText(Html.fromHtml("<a href=http://ravikiradoo.000webhostapp.com/> Ravi Shankar Kiradoo"));
        ravi.setMovementMethod(LinkMovementMethod.getInstance());


        TextView prateek = (TextView)findViewById(R.id.prateekPaliwal);
        prateek.setText(Html.fromHtml("<a href=https://github.com/paliwalprateek1> Prateek Paliwal"));
        prateek.setMovementMethod(LinkMovementMethod.getInstance());



    }
}
