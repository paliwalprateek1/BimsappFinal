package com.example.prateek.bimsapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.vision.text.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProceedOrder extends AppCompatActivity {

    private ViewPager pager;

    ImageView cartNavigator, locationNavigator, remarksNavigator;
    TextView summaryNavigator, nextButtonTextOnProceedOrder;
    LinearLayout  nextButton, backButton, doneButton;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new ProceedOrder.MyOrderPagerAdapter(getSupportFragmentManager()));


        pager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
        pager.setOffscreenPageLimit(2);

        nextButtonTextOnProceedOrder =(TextView)findViewById(R.id.nextButtonTextOnProceedOrder);

        cartNavigator = (ImageView) findViewById(R.id.cartNavigator);
        locationNavigator = (ImageView)findViewById(R.id.addressNavigator);

        nextButton = (LinearLayout) findViewById(R.id.nextButton);
        backButton =(LinearLayout) findViewById(R.id.backButton);
        doneButton = (LinearLayout)findViewById(R.id.doneButton);

        remarksNavigator = (ImageView)findViewById(R.id.remarksNavigator);

        DrawableCompat.setTint(cartNavigator.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.black));
        DrawableCompat.setTint(locationNavigator.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
        DrawableCompat.setTint(remarksNavigator.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));



        backButton.setVisibility(View.INVISIBLE);
        doneButton.setVisibility(View.INVISIBLE);

        if(pager.getCurrentItem()!=2){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backButton.setVisibility(View.VISIBLE);
                    if(storeSharedPreferences.getUserCustomLocation(getApplicationContext())!=null) {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                        if (pager.getCurrentItem() == 1) {
                            DrawableCompat.setTint(cartNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                            DrawableCompat.setTint(locationNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.black));
                            DrawableCompat.setTint(remarksNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.colorInactive));


                        }

                        else if (pager.getCurrentItem() == 2) {
                            nextButton.setVisibility(View.INVISIBLE);
                            doneButton.setVisibility(View.VISIBLE);

                            DrawableCompat.setTint(cartNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                            DrawableCompat.setTint(locationNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                            DrawableCompat.setTint(remarksNavigator.getDrawable(),
                                    ContextCompat.getColor(getBaseContext(), R.color.black));

                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please select your location", Toast.LENGTH_SHORT).show();
                    }

                }
            });}
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
                if(pager.getCurrentItem()==1){
                    nextButton.setVisibility(View.VISIBLE);
                    doneButton.setVisibility(View.INVISIBLE);
                    //nextButtonTextOnProceedOrder.setText("NEXT");
                    //nextButtonTextOnProceedOrder.setVisibility(V);
                    DrawableCompat.setTint(cartNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(locationNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.black));
                    DrawableCompat.setTint(remarksNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));

                }else if(pager.getCurrentItem()==2){
                    DrawableCompat.setTint(cartNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(locationNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(remarksNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.black));

                }else if(pager.getCurrentItem()==3){
//                    summaryNavigator.setTextColor(getResources().getColor(R.color.black));
                    DrawableCompat.setTint(cartNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(locationNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(remarksNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));

                }else if(pager.getCurrentItem()==0){
                    DrawableCompat.setTint(cartNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.black));
                    DrawableCompat.setTint(locationNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                    DrawableCompat.setTint(remarksNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.colorInactive));

                    backButton.setVisibility(View.INVISIBLE);


                }
            }
        });//}


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), OrderFoodFinal.class);
                startActivity(in);
                finish();
            }
        });



    }

    public void cancelDialog(View view) {finish();
    }


    private class MyOrderPagerAdapter extends FragmentPagerAdapter {

        public MyOrderPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0:{
                    return OrderFragment.newInstance(0);}
                case 1: {
                    DrawableCompat.setTint(cartNavigator.getDrawable(),
                            ContextCompat.getColor(getBaseContext(), R.color.black));

                    //cartNavigator.setTextColor(getResources().getColor(R.color.black));
                    return LocationFragment.newInstance(0);
                }
                case 2: return RemarksFragment.newInstance(0);
                default: return HomeFragment.newInstance(0);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}

