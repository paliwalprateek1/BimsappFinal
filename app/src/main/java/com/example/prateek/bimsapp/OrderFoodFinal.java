package com.example.prateek.bimsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.client.core.ThreadBackgroundExecutor;
import com.firebase.client.snapshot.DoubleNode;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class OrderFoodFinal extends AppCompatActivity {

    private String address, coordinates, remarks;
    TextView nameTv, addressTv, totalTv, remarksTv, foodItemList, numberTv, subtotalTV;
    Order order = new Order();
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    ArrayList<FoodQuantity> l = new ArrayList<>();
    String itemString = "";

    ListView listView;
    Toolbar toolbar;

    boolean pendingOrderStatus= false;

    int  numberOfOrders;
    double points;

    int sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_final);


        toolbar = (Toolbar) findViewById(R.id.toolbarSummary);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Summary");


        Firebase.setAndroidContext(this);
        ref = new Firebase(Server.URL);

        numberOfOrder();



        l = storeSharedPreferences.loadFavorites(this);
        for(int i=0;i<l.size();i++){
            int j = Integer.valueOf(l.get(i).getPrice())*Integer.valueOf(l.get(i).getQuantity());
            sum = j + sum;
        }


        listView = (ListView)findViewById(R.id.orderList);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this, l);
        listView.setAdapter(orderListAdapter);

        order.setItem(l);
        order.setAddress(storeSharedPreferences.getUserCustomLocation(this));
        order.setCoordinatesLatitude(storeSharedPreferences.getUserCoordinatesLatitudes(this));
        order.setCoordinatesLongitude(storeSharedPreferences.getUserCoordinatesLongitudes(this));
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

        subtotalTV = (TextView)findViewById(R.id.subtotalTv);
       // String subtotal = getSubtotal(Integer.toString(sum));
        //subtotalTV.setText(subtotal);
        //order.setAmount(subtotal);


        numberTv = (TextView)findViewById(R.id.numberTv);
        numberTv.setText(storeSharedPreferences.getUserNumber(this));

        remarksTv = (TextView) findViewById(R.id.remarksTv);
        remarksTv.setText(storeSharedPreferences.getUserRemarks(this));

        Log.d(storeSharedPreferences.getUserCoordinatesLatitudes(this), storeSharedPreferences.getUserCoordinatesLongitudes(this));
    }


    public String getSubtotal(String total){
        double lat = Double.parseDouble(storeSharedPreferences.getUserCoordinatesLatitudes(this));
        double longitude = Double.parseDouble(storeSharedPreferences.getUserCoordinatesLongitudes(this));
        double latInfo = 23.1977, sec27 = 23.245326;
        String finalValue = total;
        Log.d("Location test", "1");
        if(lat<latInfo){
            //kudasan
            Log.d("Location test", "2");
            if(Integer.valueOf(total)<100){
                Log.d("Location test", "3");
                finalValue = Integer.toString(Integer.valueOf(total)+20);
            }
        }
        else if(lat>latInfo && lat<sec27){
            Log.d("Location test", "4");
            if(Integer.valueOf(total)<200){
                Log.d("Location test", "5");
                finalValue = Integer.toString(Integer.valueOf(total)+30);
            }
        }
        else if(lat>sec27){
            Log.d("Location test", "6");
            if(Integer.valueOf(total)<300){
                Log.d("Location test", "7");
                finalValue = Integer.toString(Integer.valueOf(total)+50);
            }
        }
        if(lat<23.133 || lat>23.284 || longitude<72.59 || longitude>72.71){
            Log.d("Location test", "8");
            finish();
            Toast.makeText(this, "Your location is out of our serivce area", Toast.LENGTH_SHORT).show();
        }



        if(numberOfOrders<=5){
            double i = Double.valueOf(finalValue);
            i = i- i/20;
            finalValue = Double.toString(i);
        }

        Log.d(Integer.toString(numberOfOrders), "hi" );


        Log.d("point  ", Double.toString(points));
        if(points>0){
            Log.d("I am cuming", Double.toString(points));
            double i = Double.valueOf(finalValue);
            i = i-points/2;
            finalValue = Double.toString(i);
            Log.d("I am going", finalValue);
//            if(i>100){
//                points = 5*(i/100);
//            }
        }
        //info lat = 23.1977
        //kuda = 23.1855
        //kudasan circle: 23.18512 72.62957
        //sargasam 23.191149 72.61523

        return finalValue;
    }

    public void numberOfOrder(){
        Firebase objRef = ref.child("User");
        Query pendingTasks = objRef.orderByChild("email").equalTo((new StoreSharedPreferences()).getUserEmail(getApplicationContext()));
        // Query pendingTasks = objRef.orderByChild("email").equalTo("prateekp987@gmail.com");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()){
                    Object v = snapshot.child("points").getValue();
                    points = Double.valueOf(v.toString());
                    Object vv = snapshot.child("numberOfOrders").getValue();
                    numberOfOrders = Integer.valueOf(vv.toString());

                }
                String finalAnswer = getSubtotal(Integer.toString(sum));
                subtotalTV.setText(finalAnswer);
                order.setAmount(finalAnswer);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Toast.makeText(this, "Kutte order kyon cancel kiya", Toast.LENGTH_SHORT).show();
    }

    public void order(View view) {
        Button b = (Button)findViewById(R.id.finalOrderButton);
        if(subtotalTV.getText().toString().equals("Calculating")) {
            Toast.makeText(this, "Wait while amount is generated", Toast.LENGTH_SHORT);
        }
        else{
            getPendingOrders();
        }
    }

    public void checkConditions(){

        if(getApplicationContext()!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!pendingOrderStatus) {
                        startOrdering();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You have pending orders", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void startOrdering(){


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

        incrementOrderNumbers();
        incrementPoints(subtotalTV.getText().toString());

        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        message();
                    }
                },
                5800
        );

        storeSharedPreferences.removeAllQuant(getApplicationContext());


    }

    public void incrementPoints(String s){
        double i = Double.valueOf(s);
        i = i/100;
        i = i*5;
        points =  i;
        final String ss = Double.toString(points);
        Firebase objRef = ref.child("User");
        Query pendingTasks = objRef.orderByChild("email").equalTo(new StoreSharedPreferences().getUserEmail(getApplicationContext()));
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("points").setValue(points);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void incrementOrderNumbers(){
        Firebase objRef = ref.child("User");
        Query pendingTasks = objRef.orderByChild("email").equalTo(new StoreSharedPreferences().getUserEmail(getApplicationContext()));
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("numberOfOrders").setValue(Integer.toString(Integer.valueOf(numberOfOrders+1)));
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void message(){
        Context c = getApplicationContext();
        if(c!=null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(OrderFoodFinal.this, "Your order has been submitted", Toast.LENGTH_SHORT).show();
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



    int pendingOrder = 1;
    private void getPendingOrders(){
        Firebase objRef = ref.child("Order");
        Query pendingTask = objRef.orderByChild("mail").equalTo((new StoreSharedPreferences()).getUserEmail(getApplicationContext()));
        //final Query pendingTask = objRef.orderByChild("mail").equalTo("bapu");

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {

                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                    Object orderStatus = snapshot.child("status").getValue();
                    if((orderStatus.toString()).equals("pending")){
                        pendingOrder = 2;
                    }
                }

                if(pendingOrder==2){
                    Toast.makeText(getApplicationContext(), "You Have pending orders", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    startOrdering();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
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
