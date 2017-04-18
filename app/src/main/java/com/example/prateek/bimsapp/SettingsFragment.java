package com.example.prateek.bimsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.firebase.client.Firebase;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
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
                Intent intent = new Intent(getActivity(), PendingOrder.class);
                startActivity(intent);
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
}
