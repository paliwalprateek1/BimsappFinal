package com.example.prateek.bimsapp;

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
    TextView nameTv, addressTv, totalTv, remarksTv, foodItemList;
    Order order = new Order();
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    ArrayList<FoodQuantity> l = new ArrayList<>();
    String itemString = "";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_final);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





//        Firebase.setAndroidContext(this);
//        ref = new Firebase(Server.URL);
//
        l = storeSharedPreferences.loadFavorites(this);
        for(int i=0;i<l.size();i++){
            System.out.println(l.get(i).getFood()+l.get(i).getQuantity()+l.get(i).getPrice());
        }

        listView = (ListView)findViewById(R.id.orderList);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this, l);
        listView.setAdapter(orderListAdapter);


//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        address = extras.getString("address");
//        coordinates = extras.getString("coordinates");
//        remarks = extras.getString("remarks");
//
//        order.setAddress(address);
//        order.setAmount("thats");
//        order.setCoordinates(coordinates);
//
//        for (int i=0;i<l.size();i++){
//
//            int k = Integer.parseInt(l.get(i).getQuantity());
//            int j = Integer.parseInt(l.get(i).getPrice());
//            int m = k*j;
//
//            itemString = itemString + l.get(i).getFood() +"     X     "+
//                    l.get(i).getQuantity() +"     =     "+ Integer.toString(m) +","+"\n";
//        }
//        itemString = itemString.substring(0, itemString.length()-2);
//
//        foodItemList = (TextView)findViewById(R.id.foodItemList);
//        foodItemList.setText(itemString);
//        order.setItem(storeSharedPreferences.loadFavorites(this));
//
//
//        order.setName(storeSharedPreferences.getUserName(this));
//        order.setMail(storeSharedPreferences.getUserEmail(this));
//        order.setNumber(storeSharedPreferences.getUserNumber(this));
//        Toast.makeText(this, storeSharedPreferences.getUserNumber(this), Toast.LENGTH_SHORT).show();
//
//        nameTv = (TextView)findViewById(R.id.nameTv);
//        nameTv.setText(storeSharedPreferences.getUserName(this));
//
//        addressTv = (TextView)findViewById(R.id.addressTv);
//        addressTv.setText(address);
//
//        totalTv = (TextView) findViewById(R.id.totalTv);
//        totalTv.setText("thats");
//
//        remarksTv = (TextView) findViewById(R.id.remarksTv);
//        remarksTv.setText(remarks);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "Kutte order kyon cancel kiya", Toast.LENGTH_SHORT).show();
    }

    public void order(View view) {

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == CANCEL_DIALOG) {
                    mDialog.cancel();
                    Toast.makeText(OrderFoodFinal.this, "Ordered", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, MenuPage.class);
        startActivity(intent);
        storeSharedPreferences.removeAllQuant(this);
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    Thread.sleep(2500);
                    OrderFoodFinal.this.finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private Handler mHandler;
    private ProgressDialog mDialog;
    private final int CANCEL_DIALOG = 1;
    private Handler mHandler2 = new Handler();

    public void cancelOrder(View view) {
        Intent intent = new Intent(this, MenuMain.class);
        startActivity(intent);
        finish();
    }

    public void cancel(View view) {
//        Intent intent = new Intent(this, MenuMain.class);
//        startActivity(intent);
//        finish();

        Intent homeIntent=new Intent(this,MenuMain.class);

        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(homeIntent);
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
