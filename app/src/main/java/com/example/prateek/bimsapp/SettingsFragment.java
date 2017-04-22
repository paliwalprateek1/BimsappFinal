package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;


public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(int index) {
        SettingsFragment fragment = new SettingsFragment();
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

    EditText feedbackEditText;
    Button feedbackButton;
    Firebase ref;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();

    LinearLayout feedbackLayout, linearLayoutTrackOrder;

    LinearLayout linearLayoutCallPhone;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Firebase.setAndroidContext(getActivity());
        ref = new Firebase(Server.URL);
        feedbackLayout = (LinearLayout)view.findViewById(R.id.feedbackLayout);
        feedbackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FeedbackForm.class);
                startActivity(intent);
            }
        });

        linearLayoutTrackOrder = (LinearLayout)view.findViewById(R.id.linearLayoutTrackOrder);
        linearLayoutTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPendingOrders();

            }
        });
//        feedbackEditText = (EditText)view.findViewById(R.id.feedbackEditText);
//
//        Firebase.setAndroidContext(getActivity());
//        ref = new Firebase(Server.URL);
//
//        feedbackButton = (Button)view.findViewById(R.id.feedbackButton);
//        feedbackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendFeedback();
//            }
//        });

        linearLayoutCallPhone = (LinearLayout)view.findViewById(R.id.linearLayoutCallPhone);
        linearLayoutCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE)!=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CALL_PHONE
                    },3);
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Server.NUMBER));
                startActivity(intent);
            }
        });

        return view;
    }
//
//    public void sendFeedback(){
//        //send feedback firebase logic
//        Feedback feedback = new Feedback();
//        feedback.setEmail(storeSharedPreferences.getUserEmail(getActivity()));
//        feedback.setName(storeSharedPreferences.getUserName(getActivity()));
//        feedback.setNumber(storeSharedPreferences.getUserNumber(getActivity()));
//        feedback.setFeedback(feedbackEditText.getText().toString());
//        Firebase newRef = ref.child("Feedback").push();
//        newRef.setValue(feedback);
//        feedbackEditText.setText(null);
//        Toast.makeText(getActivity(), "Feedback submitted", Toast.LENGTH_SHORT).show();
//    }

    public void signingOut(){
        StoreSharedPreferences s = new StoreSharedPreferences();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);
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

int pendingOrder = 1;
    private boolean getPendingOrders(){
        Firebase objRef = ref.child("Order");
        Query pendingTask = objRef.orderByChild("mail").equalTo((new StoreSharedPreferences()).getUserEmail(getActivity()));
        //final Query pendingTask = objRef.orderByChild("mail").equalTo("bapu");

        pendingTask.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot tasksSnapshot) {

                for (DataSnapshot snapshot: tasksSnapshot.getChildren()) {

                    Object orderStatus = snapshot.child("status").getValue();
                    if((orderStatus.toString()).equals("pending")){
                       pendingOrder = 2;
                        //return;
                    }
                }

                if(pendingOrder==2){
                    Intent intent = new Intent(getActivity(), PendingOrder.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "No pending Orders", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        if(pendingOrder==2){
            return true;
        }
        else{
            return false;
        }
    }
}
