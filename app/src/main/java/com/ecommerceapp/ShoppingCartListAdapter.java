package com.ecommerceapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecommerceapp.model.Image;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.UpdateCartItem;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Sagar Shedge on 5/12/16.
 */

public class ShoppingCartListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<Product> productList;

    public ShoppingCartListAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.shopping_cart_list_footerview, parent, false);
            return new FooterViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shopping_cart_liitem, parent, false);
            return new MyViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof FooterViewHolder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            updateFooterView(footerHolder);

        } else if(holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            final Product product = productList.get(position);
            List<Image> images = product.getImages();
            Glide.with(viewHolder.imgBackground.getContext())
                    .load(images.get(0).getSrc())
                    .placeholder(R.drawable.pd) // can also be a drawable
                    .error(R.drawable.dress) // will be displayed if the image cannot be loaded
                    .dontAnimate()
                    .into(viewHolder.imgBackground);
            viewHolder.name.setText(product.getTitle());
            viewHolder.prize.setText("Rs. "+product.getVariants().getPrice());
            viewHolder.imageButtoncancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    EventBus.getDefault().post(new RemoveProduct(position-1,product));
                    removeItem(view.getContext(),position);
                }
            });
        }



    }
    @Override
    public int getItemViewType (int position) {
        if(isPositionFooter(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionFooter (int position) {
        return position == productList.size();
    }

    @Override
    public int getItemCount() {
        return productList.size()+1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date,prize;
        public ImageView imgBackground;
        public ImageButton imageButtoncancle;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_product_name);
            prize = (TextView) view.findViewById(R.id.txt_product_cost);
            imgBackground = (ImageView) view.findViewById(R.id.img_listitem_product);
            imageButtoncancle = (ImageButton) view.findViewById(R.id.img_listitem_product_remove);

        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView txtSubTotal,txtNetTotal;

        public FooterViewHolder (View itemView) {
            super (itemView);
            this.txtSubTotal = (TextView) itemView.findViewById (R.id.txt_product_price_subtotal);
            this.txtNetTotal = (TextView) itemView.findViewById (R.id.txt_product_price_nettotal);
        }
    }

    void removeItem(Context context,int position){
        productList.remove(position);
        notifyDataSetChanged();
        Helper.saveJsonTocache(context,productList);
        EventBus.getDefault().post(new UpdateCartItem(productList.size()));
        if(productList.size() == 0){
            ((ShoppingCartActivity)context).hideFooterview();
        }
    }

    void updateFooterView(FooterViewHolder footerHolder){
        Double subTotal = 0.0;
        for (Product product: productList) {
            String price = product.getVariants().getPrice();
            Double bigDecimal = Double.parseDouble(price);
            subTotal = subTotal+ bigDecimal;
        }
        footerHolder.txtSubTotal.setText(String.valueOf(RoundTo2Decimals(subTotal)));
        String netTotal = AppConstant.CURRENCY+String.valueOf(RoundTo2Decimals(subTotal));
        footerHolder.txtNetTotal.setText(netTotal);
    }


    double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }


}
