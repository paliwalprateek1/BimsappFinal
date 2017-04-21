package com.example.prateek.bimsapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prateek on 3/2/17.
 */

public class Order {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public ArrayList<FoodQuantity> getItem() {
        return item;
    }

    public void setItem(ArrayList item) {
        this.item = item;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    private String remarks;
    private String address;
    private String name;
    private String amount;
    private String number;
    private String mail;

    public String getCoordinatesLatitude() {
        return coordinatesLatitude;
    }

    public void setCoordinatesLatitude(String coordinatesLatitude) {
        this.coordinatesLatitude = coordinatesLatitude;
    }

    public String getCoordinatesLongitude() {
        return coordinatesLongitude;
    }

    public void setCoordinatesLongitude(String coordinatesLongitude) {
        this.coordinatesLongitude = coordinatesLongitude;
    }

    private String coordinatesLatitude;
    private String coordinatesLongitude;
    private ArrayList<FoodQuantity> item;
    private String status;


}
