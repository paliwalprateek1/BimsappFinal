package com.example.prateek.bimsapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProceedOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    private ProceedFoodAdapter mAdapter;
    private List<FoodQuantity> foodList = new ArrayList<>();
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    TextView count, amount;
    Button ua, da, dialogOk;
    int status = 1;
    Place place;
    FoodQuantity fa = new FoodQuantity();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        foodList = storeSharedPreferences.loadFoodQuantity(this);
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
            public void onClick(View view, final int position) {
//                FoodQuantity foodQuantity = new FoodQuantity();
//                foodQuantity = foodList.get(position);
//
//                fa.setFood(foodQuantity.getFood());
//                fa.setPrice(foodQuantity.getPrice());
//                fa.setQuantity(foodQuantity.getQuantity());
//
//                Toast.makeText(ProceedOrder.this, fa.getFood()+"", Toast.LENGTH_SHORT).show();
//
//
//                final Dialog dialog = new Dialog(ProceedOrder.this);
//                dialog.setContentView(R.layout.dialog_order);
//                dialog.setTitle(foodQuantity.getFood());
//
//                count = (TextView) dialog.findViewById(R.id.count);
//                count.setText(foodQuantity.getQuantity());
//                ua = (Button) dialog.findViewById(R.id.buttonUp);
//                da = (Button) dialog.findViewById(R.id.buttonDown);
//
//                ua.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int s = Integer.parseInt(count.getText().toString());
//                        s++;
//                        count.setText(Integer.toString(s));
//                    }
//                });
//                da.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int s = Integer.parseInt(count.getText().toString());
//                        if(s>0) {
//                            s--;
//                            count.setText(Integer.toString(s));
//                        }
//                    }
//                });
//                amount = (TextView) dialog.findViewById(R.id.amount);
//                amount.setText("33");
//                dialogOk = (Button) dialog.findViewById(R.id.dialogOk);
//                dialogOk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        fa.setQuantity(count.getText().toString());
//
//                        addNewItem(fa, position);
////                        foodList.set(position, fa);
////                        //foodList.add(position, fa);
////                        mAdapter.notifyDataSetChanged();
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void addNewItem(FoodQuantity f, int p){
        foodList.set(p, f);
        mAdapter.notifyDataSetChanged();
    }

    public void pickLocation(View view) {


        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ProceedOrder.this);
        if (status != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                GooglePlayServicesUtil.getErrorDialog(status, ProceedOrder.this,
                        100).show();
            }
        }
        if (status == ConnectionResult.SUCCESS) {
            int PLACE_PICKER_REQUEST = 199;
            LatLng topLeft = new LatLng(0, 0);
            LatLng bottomRight = new LatLng(0,0);
           // if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Gandhinagar")) {
                topLeft = new LatLng(23.179860, 72.649143);
                bottomRight = new LatLng(23.249227, 72.652202);
//            }
//            else if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Vadodara")){
//                topLeft = new LatLng(22.265240, 73.144044);
//                bottomRight = new LatLng(22.381635, 73.195201);
//            }
            LatLngBounds bounds = new LatLngBounds(topLeft, bottomRight);
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            builder.setLatLngBounds(bounds);
            //PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            //Context context = this;
            try {
                startActivityForResult(builder.build(ProceedOrder.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        }
        if (requestCode == 199) {
            if (data != null) {
                place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getAddress());
                Toast.makeText(getApplication(), toastMsg, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Select your location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
