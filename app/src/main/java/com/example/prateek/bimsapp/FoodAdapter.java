package com.example.prateek.bimsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by prateek on 7/10/16.
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    private List<Food> foodList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView food, price;
        public ImageView foodItemIcon;

        public MyViewHolder(View view) {
            super(view);
            food = (TextView) view.findViewById(R.id.food);
            price = (TextView) view.findViewById(R.id.price);
            foodItemIcon = (ImageView) view.findViewById(R.id.foodItemIcon);
        }
    }


    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.food.setText(food.getFood());
        holder.price.setText(food.getPrice()+" Rs");


        Picasso.with(holder.foodItemIcon.getContext())
                .load(food.getImageUrl())
                .transform(new CircleTransform())
                .into(holder.foodItemIcon);


        int w = 1024;
        int h = 768;
        int size = (int) Math.ceil(Math.sqrt(w*h));

//        Picasso.with(holder.foodItemIcon.getContext())
//                .load(food.getImageUrl())
//                .transform(new BitmapTransform(w, h))
//                .skipMemoryCache()
//                .resize(size, size)
//                .centerInside()
//                .into(holder.foodItemIcon);

        Picasso.with(holder.foodItemIcon.getContext())
                .load(food.getImageUrl())
                .transform(new CircleTransform())
                .into(holder.foodItemIcon);





//        Bitmap image=null;
//        try {
//            URL url = new URL(food.getImageUrl());
//            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch(IOException e) {
//            System.out.println(e);
//        }
//
//        holder.foodItemIcon.setImageBitmap(cropToSquare(image));





    }


    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public static Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }
}

class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size + 1) / 2;
        int y = (source.getHeight() - size + 1) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}

class BitmapTransform implements Transformation{
    private final int maW;
    private final int maH;

    public BitmapTransform(int maW, int maH){
        this.maH = maH;
        this.maW = maW;
    }

    @Override
    public Bitmap transform(Bitmap source){
        int taW, taH;
        double aspectRatio;

        if(source.getWidth()>source.getHeight()){
            taW = maW;
            aspectRatio = (double)source.getHeight()/(double)source.getWidth();
            taH = (int) (taW *aspectRatio);
        }
        else {
            taH = maH;
            aspectRatio = (double)source.getWidth()/(double)source.getHeight();
            taW = (int) (taH *aspectRatio);
        }

        Bitmap result = Bitmap.createScaledBitmap(source, taW, taH, false);
        if(result!=source){
            source.recycle();
        }
        return result;
    }

    @Override
    public String key(){
        return maW +"x"+maH;
    }
}



