package com.example.prateek.bimsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class QQ extends AppCompatActivity {

    Toolbar toolbar;
    Firebase ref;
    ImageView homeSmallIcon, menuSmallIcon, orderSmallIcon, accountSmallIcon, settingsSmallIcon;
    LinearLayout homeSmallIconrl, menuSmallIconrl, orderSmallIconrl, accountSmallIconrl, settingsSmallIconrl;;

    private ViewPager pager;
    StoreSharedPreferences store = new StoreSharedPreferences();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Firebase.setAndroidContext(this);

        ref = new Firebase(Server.URL);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(store.getKitchenName(this));



        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        menuSmallIcon = (ImageView)findViewById(R.id.menuSmallIcon);
        homeSmallIcon = (ImageView)findViewById(R.id.homeSmallIcon);
        orderSmallIcon = (ImageView)findViewById(R.id.orderSmallIcon);
        accountSmallIcon = (ImageView)findViewById(R.id.accountSmallIcon);
        settingsSmallIcon = (ImageView)findViewById(R.id.settingsSmallIcon);

        Toast.makeText(this, store.getUserEmail(this), Toast.LENGTH_SHORT).show();


        if(store.getState(this).length()!=1){
        }
        else {
            pager.setCurrentItem(Integer.valueOf(store.getState(QQ.this)));
            store.setState(this, null);
        }


        pager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
        pager.setOffscreenPageLimit(5);
        homeSmallIconrl = (LinearLayout) findViewById(R.id.homeSmallIconrl);
        menuSmallIconrl = (LinearLayout) findViewById(R.id.menuSmallIconrl);
        accountSmallIconrl = (LinearLayout) findViewById(R.id.accountSmallIconrl);
        orderSmallIconrl = (LinearLayout) findViewById(R.id.orderSmallIconrl);
        settingsSmallIconrl = (LinearLayout) findViewById(R.id.settingsSmallIconrl);

        menuSmallIconrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QQ.this, MenuPage.class);
                startActivity(intent);
            }
        });


        homeSmallIconrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawableCompat.setTint(orderSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(menuSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(homeSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                DrawableCompat.setTint(accountSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(settingsSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));

                pager.setCurrentItem(0);
            }
        });

        orderSmallIconrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(store.loadFavorites(getApplicationContext())!=null) {
                    Intent intent = new Intent(getApplicationContext(), ProceedOrder.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "No Items Selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        accountSmallIconrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawableCompat.setTint(orderSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(menuSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(homeSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(accountSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                DrawableCompat.setTint(settingsSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                pager.setCurrentItem(3);
            }
        });
        settingsSmallIconrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DrawableCompat.setTint(orderSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(menuSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(homeSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(accountSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
                DrawableCompat.setTint(settingsSmallIcon.getDrawable(),
                        ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                pager.setCurrentItem(4);
            }
        });

        DrawableCompat.setTint(orderSmallIcon.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
        DrawableCompat.setTint(menuSmallIcon.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
        DrawableCompat.setTint(homeSmallIcon.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        DrawableCompat.setTint(accountSmallIcon.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));
        DrawableCompat.setTint(settingsSmallIcon.getDrawable(),
                ContextCompat.getColor(getBaseContext(), R.color.colorInactive));




    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return HomeFragment.newInstance(0);
                case 1: return MenuFragment.newInstance(0);
                case 2: return OrderFragment.newInstance(0);
                case 3: return AccountFragment.newInstance(0);
                case 4: return SettingsFragment.newInstance(0);
                default: return HomeFragment.newInstance(0);
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}