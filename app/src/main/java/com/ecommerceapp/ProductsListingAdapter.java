package com.ecommerceapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecommerceapp.model.Image;
import com.ecommerceapp.model.Product;

import java.util.List;

/**
 * Created by Sagar Shedge on 29/11/16.
 */

public class ProductsListingAdapter extends RecyclerView.Adapter<ProductsListingAdapter.MyViewHolder> {

    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date,prize;
        public ImageView imgBackground;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_product_name);
//            date = (TextView) view.findViewById(R.id.txt_event_date);
            prize = (TextView) view.findViewById(R.id.txt_product_cost);
            imgBackground = (ImageView) view.findViewById(R.id.img_listitem_product);
        }
    }


    public ProductsListingAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_griditem_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Product product = productList.get(position);
        List<Image> images = product.getImages();
        Glide.with(holder.imgBackground.getContext())
                .load(images.get(0).getSrc())
                .placeholder(R.drawable.pd) // can also be a drawable
                .error(R.drawable.dress) // will be displayed if the image cannot be loaded
                .dontAnimate()
                .into(holder.imgBackground);

     /*   long  time = Long.parseLong(event.getEventStarttime());
        SimpleDateFormat outputFormat1 = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
        SimpleDateFormat outputFormat2 = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);

        Date parsedDate = new Date(time);
        String formattedDate = outputFormat1.format(parsedDate);
        String formattedTime = outputFormat2.format(parsedDate);*/

       /* holder.date.setText(formattedDate);
        holder.time.setText(formattedTime);*/

        holder.name.setText(product.getTitle());
        holder.prize.setText("Rs. "+product.getVariants().getPrice());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}

