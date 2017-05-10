package com.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ecommerceapp.model.Image;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.UpdateCartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ProductDetailsActivity extends AppCompatActivity {
    private Context mContext;
    private TextView mTxtToolbarTitle,txtDescription,txtName,txtType,txtPrize,txtBrand,txtVender;
    private Toolbar mToolbar;
    private Product product;
    private CustomProgressDialog customProgressDialog;
    private static final int REQUEST_CODE_DELETE_EVENT = 101;
    private AppAlertDialog appAlertDialog;
    private Button btnAddToCart,btnBuyNow;

    private static ViewPager mPager;

    private ArrayList<String> ImagesArray = new ArrayList<String>();

    private LayerDrawable mCartMenuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mContext = ProductDetailsActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTxtToolbarTitle = (TextView) mToolbar.findViewById(R.id.lblTitle);
        mTxtToolbarTitle.setText("Product Details");
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customProgressDialog = new CustomProgressDialog(mContext, false);
        if(getIntent().getExtras() != null){
            product = getIntent().getExtras().getParcelable("product_details");
        }
        EventBus.getDefault().register(this);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu); mCartMenuIcon = (LayerDrawable) menu.findItem(R.id.action_cart).getIcon();
        List<Product> productList = Helper.retrieveJsonTocache(mContext);
        setBadgeCount(this, mCartMenuIcon, String.valueOf(productList.size()));
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;
            case R.id.action_cart: {
                Intent intent = new Intent(mContext, ShoppingCartActivity.class);
                startActivity(intent);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void init() {
        List<Image> images = product.getImages();
        for(int i=0;i<images.size();i++) {
            Image image = images.get(i);
            ImagesArray.add(image.getSrc());
        }
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImageAdapter(ProductDetailsActivity.this,ImagesArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        btnAddToCart = (Button) findViewById(R.id.btn_addtocart);
        btnBuyNow = (Button) findViewById(R.id.btn_buynow);

        txtName = (TextView) findViewById(R.id.txt_product_name);
        txtPrize = (TextView) findViewById(R.id.txt_product_cost);
        txtType = (TextView) findViewById(R.id.txt_product_type);
        txtBrand = (TextView) findViewById(R.id.txt_product_brand);
        txtDescription = (TextView) findViewById(R.id.txt_product_description);
        txtVender = (TextView) findViewById(R.id.txt_product_vender);

        txtName.setText(product.getTitle());
        txtPrize.setText("Rs. "+product.getVariants().getPrice());
        txtBrand.setText(product.getTitle());
        txtVender.setText(product.getVendor());
        txtType.setText(product.getProductType());
        txtDescription.setText(product.getTitle());


        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              List<Product>  productList = Helper.retrieveJsonTocache(mContext);
              for(Product product1 : productList){
                  if(product1.getId() == product.getId()){
                      Helper.alertSnackbar(mContext,mToolbar,"only 1 quantity available for this product", true);
                      return;
                  }
              }
              productList.add(product);
              Helper.saveJsonTocache(mContext,productList);
              EventBus.getDefault().post(new UpdateCartItem(productList.size()));
            }
        });

    }
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }
    public void onClickIncrementCartCount(int count) {
        setBadgeCount(this, mCartMenuIcon, String.valueOf(count));
    }

    @Subscribe
    public void onEvent(UpdateCartItem updateCartItem) {
        onClickIncrementCartCount(updateCartItem.count);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}