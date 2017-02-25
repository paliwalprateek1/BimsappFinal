package com.example.prateek.bimsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private RecyclerView mRecyclerView, mRecyclerView2;
    private CardAdapter mAdapter2;
    private FeaturedCardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager, mLayoutManager2;
    private List<Food> foodListVeg = new ArrayList<>();
    private List<Food> foodListNon = new ArrayList<>();
    Firebase ref;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
    }


    public static HomeFragment newInstance(int index) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ref = new Firebase(Server.URL);

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        if(foodListVeg.size()==0) {
            getNonVeg();getVeg();
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == CANCEL_DIALOG) {
                        mDialog.cancel();
                    }

                    return false;
                }
            });
            mDialog = new ProgressDialog(getActivity());
            mDialog.setMessage("Starting Application....");
            mDialog.show();
            mHandler.sendEmptyMessageDelayed(CANCEL_DIALOG, 5500);
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.hrlist_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.hrlist_recycler_view1);
        mRecyclerView2.setHasFixedSize(true);

        mAdapter2 = new CardAdapter(foodListVeg);
        mRecyclerView2.setAdapter(mAdapter2);

        mAdapter = new FeaturedCardAdapter(foodListNon);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView2.setLayoutManager(mLayoutManager2);



        return view;


    }
    private Handler mHandler;
    private ProgressDialog mDialog;
    private final int CANCEL_DIALOG = 1;

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener=null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void getVeg() {
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("veg");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());
                    food.setAvailability(null);
                    food.setRating(null);
                    foodListVeg.add(food);
                    mAdapter.notifyDataSetChanged();
                    Log.d("food " + value.toString(), "price " + valueF.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void getNonVeg() {
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("nonveg");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                    Object value = snapshot.child("f").getValue();
                    Object valueF = snapshot.child("p").getValue();
                    Object valueU = snapshot.child("url").getValue();
                    Log.d(valueU.toString(), "url che");
                    Food food = new Food();
                    food.setPrice(valueF.toString());
                    food.setFood(value.toString());
                    food.setImageUrl(valueU.toString());
                    food.setAvailability(null);
                    food.setRating(null);
                    foodListNon.add(food);
                    mAdapter.notifyDataSetChanged();
                    Log.d("food " + value.toString(), "price " + valueF.toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
}
