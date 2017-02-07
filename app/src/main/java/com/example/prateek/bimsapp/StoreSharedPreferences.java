package com.example.prateek.bimsapp;

/**
 * Created by prateek on 21/1/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StoreSharedPreferences {

    public static final String PREFS_MAIL = "email";
    public static final String PREFS_USER_NAME = "name";
    public static final String PREFS_USER_CUSTOM_LOCATION = "location";
    public static final String NUMBER = "number";



    public static final String PREFS_NAME = "NKDROID_APP_QUANT";
    public static final String FAVORITES = "Favorite_QUANT";


    public StoreSharedPreferences() {
        super();
    }

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }


    public void removeAllQuant(Context context){
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public void storeFavorites(Context context, List favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }


    public ArrayList loadFavorites(Context context) {
        SharedPreferences settings;
        List favorites;
        settings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            FoodQuantity[] favoriteItems = gson.fromJson(jsonFavorites,FoodQuantity[].class);
            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList(favorites);
        } else
            return null;
        return (ArrayList) favorites;
    }
    public void addFavorite(Context context, FoodQuantity beanSampleList) {
        List favorites = loadFavorites(context);
        if (favorites == null)
            favorites = new ArrayList();
        favorites.add(beanSampleList);
        storeFavorites(context, favorites);
    }
    public void removeFavorite(Context context, FoodQuantity beanSampleList) {
        ArrayList favorites = loadFavorites(context);
        if (favorites != null) {
            favorites.remove(beanSampleList.getFood());
            removeAllQuant(context);
            Log.d("here is mulakjdf==", favorites.size()+"  adfk");
            storeFavorites(context, favorites);
        }
    }

    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////
    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////    /////////////////////////////////////////
    ////////////////////////////////////////
    ////////////////////////////////////////

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREFS_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx){
        return getSharedPreferences(ctx).getString("name", "");
    }

    public static void setUserCustomLocation(Context ctx, String userCustomLocation){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREFS_USER_CUSTOM_LOCATION, userCustomLocation);
        editor.commit();
    }

    public static String getUserCustomLocation(Context ctx){
        return getSharedPreferences(ctx).getString("location", "");
    }

    public static void setUserEmail(Context ctx, String userMail) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREFS_MAIL, userMail);
        editor.commit();
    }

    public static String getUserEmail(Context ctx) {
        return getSharedPreferences(ctx).getString("email", "");
    }

    public static void setUserNumber(Context ctx, String userNumber) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(NUMBER, userNumber);
        editor.commit();
    }

    public static String getUserNumber(Context ctx) {
        return getSharedPreferences(ctx).getString("number", "");
    }
}
