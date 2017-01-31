package com.example.prateek.bimsapp;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Veg extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public Veg() {
    }

    public static Veg newInstance(String param1, String param2) {
        Veg fragment = new Veg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    RecyclerView recyclerView;
    private FoodAdapter mAdapter;
    private List<Food> foodList = new ArrayList<>();
    Firebase ref;
    Food food = new Food();
    Button dialogOk, ua, da;
    TextView count;
    FoodQuantity fa = new FoodQuantity();
    NumberPicker np;
    RelativeLayout rl;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Firebase.setAndroidContext(getActivity());

        ref = new Firebase(Server.URL);
        if(foodList.size()==0){
            getVegMenu();
        }
        View view = inflater.inflate(R.layout.fragment_veg, container, false);

       // foodList.add(food);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new FoodAdapter(foodList);
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
                StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();
                storeSharedPreferences.removeAllQuant(getActivity());

            }
        }));
        return view;
    }
    public void setValue(String str){
        fa.setQuantity(str);
    }

    public void storeData(FoodQuantity fq){
        StoreSharedPreferences s = new StoreSharedPreferences();
        s.addFoodQuantity(getActivity(), fq);

    }


    private void getVegMenu(){
        Firebase objRef = ref.child("Menu");
        Query pendingTasks = objRef.orderByChild("cat").equalTo("veg");
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
