package com.example.prateek.bimsapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class AccountFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
    }

    public static AccountFragment newInstance(int index) {
        AccountFragment fragment = new AccountFragment();
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

    Firebase ref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase(Server.URL);




        Button b = (Button)view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                method();
            }
        });

        Button change = (Button)view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMehtod();
            }
        });
        return view;
    }


    public void changeMehtod(){
        //Firebase m_objFireBaseRef = new Firebase(AppConstants.FIREBASE_URL);
        Firebase objRef = ref.child("User");
        Query pendingTasks = objRef.orderByChild("email").equalTo("prateekp987@gmail.com");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {
                    snapshot.getRef().child("points").setValue("25");
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public void method(){
        Firebase objRef = ref.child("User");
        //Query pendingTasks = objRef.orderByChild("email").equalTo((new StoreSharedPreferences()).getUserEmail(getActivity()));
        Query pendingTasks = objRef.orderByChild("email").equalTo("prateekp987@gmail.com");
        pendingTasks.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {
                for (DataSnapshot snapshot : tasksSnapshot.getChildren()){
                    Object v = snapshot.child("points").getValue();
                    Object vv = snapshot.child("number").getValue();
                    System.out.println(v + " adg "+vv);
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

//
//        ref.child("User").orderByChild("email").equalTo("prateekp987@gmail.com").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                System.out.println(snapshot.getValue());
//            }
//            @Override public void onCancelled(FirebaseError error) { }
//        });
    }

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
}
