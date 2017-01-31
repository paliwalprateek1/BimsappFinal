package com.example.prateek.bimsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProceedOrder extends AppCompatActivity {


    RecyclerView recyclerView;
    private ProceedFoodAdapter mAdapter;
    private List<FoodQuantity> foodList = new ArrayList<>();
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        foodList = storeSharedPreferences.loadFoodQuantity(this);
        //storeSharedPreferences.removeAllQuant(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_proceed_order);
        mAdapter = new ProceedFoodAdapter(foodList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                FoodQuantity fq = new FoodQuantity();
                fq = foodList.get(position);
                FoodQuantity e = new FoodQuantity();
                e.setQuantity("44");
                e.setPrice("33");
                e.setFood("maaa");
                foodList.add(2, e);
                fq.getQuantity();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
