package com.example.prateek.bimsapp;

/**
 * Created by prateek on 7/10/16.
 */
public class Food {
    private String food, price, imageUrl, rating, availability;

        public Food() {
        }

        public Food(String food, String price, String imageUrl) {
            this.food = food;
            this.price = price;
            this.imageUrl = imageUrl;
        }



        public String getFood() {
            return food;
        }

        public void setFood(String name) {
            this.food = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}

        public String getImageUrl(){ return  imageUrl;}




}
