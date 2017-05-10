package com.ecommerceapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ecommerceapp.model.Product;
import com.ecommerceapp.model.RemoveProduct;
import com.ecommerceapp.model.UpdateCartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private Context mContext;
    private TextView mTxtToolbarTitle;
    private Toolbar mToolbar;
    private CustomProgressDialog customProgressDialog;
    private static final int REQUEST_CODE_DELETE_EVENT = 101;
    private AppAlertDialog appAlertDialog;
    private RecyclerView recyclerView;
    private ShoppingCartListAdapter shoppingCartListAdapter;
    private List<Product> productList = new ArrayList<>();
    private TextView lableNodataFound;
    private SwipeRefreshLayout swipeContainer;
    private Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        mContext = ShoppingCartActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTxtToolbarTitle = (TextView) mToolbar.findViewById(R.id.lblTitle);
        mTxtToolbarTitle.setText("Shopping Cart");
        try {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customProgressDialog = new CustomProgressDialog(mContext, false);
        btnContinue = (Button) findViewById(R.id.btn_buy_coutinue);
        lableNodataFound = (TextView) findViewById(R.id.label_no_cart_item_found);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {
                swipeContainer.setRefreshing(false);

//                Apis.getInstance(mContext).getProducts(ProductListingFragment.this, REQUEST_CODE_GET_PRODUCTS);

            }

        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        productList = Helper.retrieveJsonTocache(mContext);
        if (shoppingCartListAdapter == null) {
            shoppingCartListAdapter = new ShoppingCartListAdapter(productList);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_shopping_cart);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shoppingCartListAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
              /*  Intent intent = new Intent(mContext, ProductDetailsActivity.class);
//                intent.putExtra("product_details",productList.get(position));
                startActivity(intent);*/


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if (productList.size() == 0) {
            hideFooterview();
        }
        EventBus.getDefault().register(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onEvent(RemoveProduct removeProduct) {
        productList.remove(removeProduct.position);
        shoppingCartListAdapter.notifyItemRemoved(removeProduct.position);
        Helper.saveJsonTocache(mContext,productList);
        EventBus.getDefault().post(new UpdateCartItem(productList.size()));

    }

    public void hideFooterview(){
        lableNodataFound.setVisibility(View.VISIBLE);
        btnContinue.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
