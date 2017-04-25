package com.example.prateek.bimsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class LocationFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LocationFragment() {
    }

    public static LocationFragment newInstance(int index) {
        LocationFragment fragment = new LocationFragment();
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

    Button buttonPickLocation;
    int status = 1;
    Place place;
    TextView textViewAddress, textViewAddress2;
    StoreSharedPreferences storeSharedPreferences = new StoreSharedPreferences();


    private Handler mHandler;
    private ProgressDialog mDialog;
    private final int CANCEL_DIALOG = 1;
    private Handler mHandler2 = new Handler();
    RadioButton radioButton, radioButton1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_location, container, false);
        textViewAddress2 =(TextView)view.findViewById(R.id.textViewAddress2);

        if(storeSharedPreferences.getUserCustomLocation(getActivity())!=null){
            textViewAddress2.setText(storeSharedPreferences.getUserCustomLocation(getActivity()));
        }
        buttonPickLocation = (Button)view.findViewById(R.id.buttonPickLocation);
        buttonPickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                mDialog.setMessage("Please Wait..");
                mDialog.show();
                mHandler.sendEmptyMessageDelayed(CANCEL_DIALOG, 3500);
                status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
                if (status != ConnectionResult.SUCCESS) {
                    if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
                        GooglePlayServicesUtil.getErrorDialog(status, getActivity(),
                                100).show();
                    }
                }
                if (status == ConnectionResult.SUCCESS) {
                    int PLACE_PICKER_REQUEST = 199;
                    LatLng topLeft = new LatLng(0, 0);
                    LatLng bottomRight = new LatLng(0,0);
                    // if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Gandhinagar")) {
                    topLeft = new LatLng(23.179860, 72.649143);
                    bottomRight = new LatLng(23.249227, 72.652202);
//            }
//            else if(StoreSharedPreferences.getUserCustomLocation(ProceedOrder.this).equals("Vadodara")){
//                topLeft = new LatLng(22.265240, 73.144044);
//                bottomRight = new LatLng(22.381635, 73.195201);
//            }
                    LatLngBounds bounds = new LatLngBounds(topLeft, bottomRight);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    builder.setLatLngBounds(bounds);
                    try {
                        startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        return view;
    }


    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        }
        if (requestCode == 199) {
            if (data != null) {
                place = PlacePicker.getPlace(data, getActivity());
                LatLngBounds place2 = PlacePicker.getLatLngBounds(data);

                textViewAddress2.setText(place.getAddress());
                String s = place.getLatLng().toString();
                s = s.substring(10, s.length()-1);
                String[] a = s.split(",");

                if(place.getAddress()!=null) {
                    storeSharedPreferences.setUserCustomLocation(getActivity(), place.getAddress().toString());
                }else{
                    Toast.makeText(getActivity(), "Select Precise Address", Toast.LENGTH_SHORT).show();
                }
                storeSharedPreferences.setUserCoordinatesLatitudes(getActivity(), a[0]);
                storeSharedPreferences.setUserCoordinatesLongitudes(getActivity(), a[1]);

            } else {
                Toast.makeText(getActivity(), "Select your location", Toast.LENGTH_LONG).show();
            }
        }
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
