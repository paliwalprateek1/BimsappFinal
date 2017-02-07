package com.example.prateek.bimsapp;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Bev extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Bev() {
    }


    public static Bev newInstance(String param1, String param2) {
        Bev fragment = new Bev();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
    Button dialogOk, ua, da;
    TextView count;
    FoodQuantity fa = new FoodQuantity();


    private List<Food> foodList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FoodAdapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Firebase.setAndroidContext(getActivity());

        ref = new Firebase(Server.URL);

        View view = inflater.inflate(R.layout.fragment_bev, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new FoodAdapter(foodList);


        if(foodList.size()==0){
            getBevMenu();
        }
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Food f = new Food();
                f = foodList.get(position);
                fa.setPrice(f.getPrice());
                fa.setFood(f.getFood());

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_counter);
                dialog.setTitle(f.getFood());
                ImageView image = (ImageView) dialog.findViewById(R.id.image);
                ImageView dialogImage = (ImageView) dialog.findViewById(R.id.dialogImage);

                Log.d("her image url is", f.getImageUrl()+"");

                count = (TextView) dialog.findViewById(R.id.count);
                count.setText("0");
                ua = (Button) dialog.findViewById(R.id.buttonUp);
                da = (Button) dialog.findViewById(R.id.buttonDown);

                ua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int s = Integer.parseInt(count.getText().toString());
                        s++;
                        count.setText(Integer.toString(s));
                    }
                });

                da.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int s = Integer.parseInt(count.getText().toString());
                        if(s>0) {
                            s--;
                            count.setText(Integer.toString(s));
                        }
                    }
                });

                Picasso.with(dialogImage.getContext())
                        .load(f.getImageUrl())
                        .transform(new CircleTransform())
                        .into(dialogImage);

                dialogOk = (Button) dialog.findViewById(R.id.counterOk);

                dialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(!(count.getText().toString()).equals("0")) {
                            setValue(count.getText().toString());
                            storeData(fa);
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        return view;
    }
    public void setValue(String str){
        fa.setQuantity(str);
    }

    public void storeData(FoodQuantity fq){
        StoreSharedPreferences s = new StoreSharedPreferences();
        s.addFavorite(getActivity(), fq);

    }
    Firebase ref;

    private void getBevMenu(){
        //final Food food = new Food(null, null);
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("bev");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
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
                    foodList.add(food);
                    mAdapter.notifyDataSetChanged();
                    Log.d("food "+value.toString(), "price "+valueF.toString());
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = null;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
