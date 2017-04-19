package com.example.prateek.bimsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PendingOrder extends AppCompatActivity {

    Firebase ref;
    Order pendingOrder = new Order();
    Toolbar toolbar;
    TextView address, status, amount;
    ListView listView;

    ArrayList<Order> pend = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Order Status");

        Firebase.setAndroidContext(this);
        ref=new Firebase(Server.URL);

        getPendingOrders();


    }


    private void getPendingOrders(){
        Firebase objRef = ref.child("Order");
        //Query pendingTask = objRef.orderByChild("mail").equalTo((new StoreSharedPreferences()).getUserEmail(this));
        final Query pendingTask = objRef.orderByChild("mail").equalTo("bapu");

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {

                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                    Object orderStatus = snapshot.child("status").getValue();
                    if((orderStatus.toString()).equals("pending")){
                        pendingOrder = snapshot.getValue(Order.class);
                        arrangeOrder(pendingOrder);
                        pend.add(pendingOrder);
                        System.out.println("Here bitches"+ pendingOrder.getAmount());
                        //return;
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }
    public void arrangeOrder(Order pendingOrder){
        //for(int i=0;i<pend.size();i++) {
           // if(pend.get(i).getAmount()!=null) {
                address = (TextView) findViewById(R.id.textViewPendingAddress);
                address.setText(pendingOrder.getAddress());

                status = (TextView) findViewById(R.id.textViewPendingStatus);
                status.setText(pendingOrder.getStatus());

                amount = (TextView) findViewById(R.id.textViewPendingAmount);
                amount.setText(pendingOrder.getAmount());

        //System.out.println(pendingOrder.getItem());

        String s = pendingOrder.getItem().toString();
        System.out.println(s+" otawa");
        s=s.substring(2, s.length()-2);
        ArrayList<FoodQuantity> alf = new ArrayList<>();
        String[] a = s.split(", ");
        for (int j = 0;j<a.length;j++){
            System.out.println(a[j]);
            FoodQuantity fq = new FoodQuantity();
            String s1[] = a[j].split("=");
            fq.setQuantity(s1[1]);

            j=j+1;
            String s2[] = a[j].split("=");
            fq.setFood(s2[1]);

            j=j+1;
            String s3[] = a[j++].split("=");
            fq.setPrice(s3[1]);
            fq.setUrl(null);

            alf.add(fq);
        }




                listView = (ListView)findViewById(R.id.listPending);
                OrderListAdapter orderListAdapter = new OrderListAdapter(this,
                        alf);
                listView.setAdapter(orderListAdapter);
            //}
       // }

    }
}
