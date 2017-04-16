package com.example.prateek.bimsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.ThreadBackgroundExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class OrderFoodFinal extends AppCompatActivity {

    private String address, coordinates, remarks;
    TextView nameTv, addressTv, totalTv, remarksTv, foodItemList, numberTv;
    Order order = new Order();
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    ArrayList<FoodQuantity> l = new ArrayList<>();
    String itemString = "";

    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_final);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbarSummary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Summary");


        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);
//
          l = storeSharedPreferences.loadFavorites(this);
        int sum=0;
        for(int i=0;i<l.size();i++){
            int j = Integer.valueOf(l.get(i).getPrice())*Integer.valueOf(l.get(i).getQuantity());
            sum = j + sum;
        }


        listView = (ListView)findViewById(R.id.orderList);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this, l);
        listView.setAdapter(orderListAdapter);

        order.setItem(l);
        order.setAddress(storeSharedPreferences.getUserCustomLocation(this));
        order.setAmount(Integer.toString(sum));
        order.setCoordinates(storeSharedPreferences.getUserCoordinates(this));
        order.setName(storeSharedPreferences.getUserName(this));
        order.setMail(storeSharedPreferences.getUserEmail(this));
        order.setNumber(storeSharedPreferences.getUserNumber(this));
        order.setStatus("pending");
        order.setRemarks(storeSharedPreferences.getUserRemarks(this));


        //Toast.makeText(this, storeSharedPreferences.getUserNumber(this), Toast.LENGTH_SHORT).show();

        nameTv = (TextView)findViewById(R.id.nameTv);
        nameTv.setText(storeSharedPreferences.getUserName(this));

        addressTv = (TextView)findViewById(R.id.addressTv);
        addressTv.setText(storeSharedPreferences.getUserCustomLocation(this));

        totalTv = (TextView) findViewById(R.id.totalTv);
        totalTv.setText(Integer.toString(sum));

        numberTv = (TextView)findViewById(R.id.numberTv);
        numberTv.setText(storeSharedPreferences.getUserNumber(this));

        remarksTv = (TextView) findViewById(R.id.remarksTv);
        remarksTv.setText(storeSharedPreferences.getUserRemarks(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Toast.makeText(this, "Kutte order kyon cancel kiya", Toast.LENGTH_SHORT).show();
    }

    public void order(View view) {

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == CANCEL_DIALOG && (mDialog!=null)) {
                    mDialog.cancel();
                }
                return false;
            }
        });
        mDialog = new ProgressDialog(OrderFoodFinal.this);
        mDialog.setMessage("Ordering.. Please Wait..");
        mDialog.show();
        mHandler.sendEmptyMessageDelayed(CANCEL_DIALOG, 5500);

        Firebase.setAndroidContext(getApplicationContext());
        ref = new Firebase(Server.URL);
        Firebase newRef = ref.child("Order").push();
        newRef.setValue(order);

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        message();
                    }
                },
                5800
        );

        storeSharedPreferences.removeAllQuant(this);
    }

    public void message(){

        Context c = getApplicationContext();
        if(c!=null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(OrderFoodFinal.this, "Ordered", Toast.LENGTH_SHORT).show();
//                    Intent homeIntent=new Intent(getApplicationContext(),MenuMain.class);
//
//                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                    startActivity(homeIntent);
                    finish();
                }
            });
        }

    }

    private Handler mHandler;
    private ProgressDialog mDialog;
    private final int CANCEL_DIALOG = 1;
    private Handler mHandler2 = new Handler();

    public void cancelOrder(View view) {
//        Intent homeIntent=new Intent(this,MenuMain.class);
//
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(homeIntent);
        finish();
    }

    public void cancel(View view) {
//
//        Intent homeIntent=new Intent(this,MenuMain.class);
//
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(homeIntent);
        finish();
    }
}



class OrderListAdapter extends ArrayAdapter<FoodQuantity> {

    private ArrayList<FoodQuantity> items;
    private TextView title,title1,title2;
    private Context context;

    public OrderListAdapter(Context context, ArrayList<FoodQuantity> i) {
        super(context,0);
        this.context = context;
        items = new ArrayList<>();
        this.items = i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null){
            v = LayoutInflater.from(context).inflate(R.layout.order_list,parent,false);
        }

        title = (TextView) v.findViewById(R.id.title);
        title1 = (TextView) v.findViewById(R.id.title2);
        title2 = (TextView) v.findViewById(R.id.title1);
        title.setText(items.get(position).getFood());
        title1.setText("  x  "+items.get(position).getQuantity()+"  =  ");
        int j = Integer.valueOf(items.get(position).getQuantity());
        int k = Integer.valueOf(items.get(position).getPrice());
        title2.setText(Integer.toString(j*k));
        return v;
    }

    @Override
    public int getCount() {
        return items.size();
    }


}
