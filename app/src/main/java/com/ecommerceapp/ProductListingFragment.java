package com.ecommerceapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecommerceapp.model.APIError;
import com.ecommerceapp.model.GetProductsResponse;
import com.ecommerceapp.model.Product;
import com.ecommerceapp.network.Apis;
import com.ecommerceapp.network.ServiceGenerator;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListingFragment extends Fragment implements Apis.ApisCallbackInterface {
    private static final int REQUEST_CODE_GET_PRODUCTS = 102;
    private RecyclerView recyclerView;
    private ProductsListingAdapter productsListingAdapter;
    private List<Product> productList = new ArrayList<>();
    private TextView lableNodataFound;
    private CustomProgressDialog customProgressDialog;
    private Context mContext;
    private SwipeRefreshLayout swipeContainer;

    public ProductListingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_listing, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customProgressDialog = new CustomProgressDialog(mContext, false);
        lableNodataFound = (TextView) view.findViewById(R.id.label_no_products_found);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                Apis.getInstance(mContext).getProducts(ProductListingFragment.this, REQUEST_CODE_GET_PRODUCTS);

            }

        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

      if (productsListingAdapter == null) {
            productsListingAdapter = new ProductsListingAdapter(productList);
        }
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_products_list);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(productsListingAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra("product_details",productList.get(position));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        customProgressDialog.show();
        Apis.getInstance(mContext).getProducts(this, REQUEST_CODE_GET_PRODUCTS);
    }

    @Override
    public void apiSuccessCallBack(int requestCode, Object response, Object tag) {
        if (customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
        swipeContainer.setRefreshing(false);

        if (requestCode == REQUEST_CODE_GET_PRODUCTS) {
            if (response instanceof GetProductsResponse) {
                GetProductsResponse getProductsResponse = (GetProductsResponse) response;
               if (getProductsResponse.getStatus()) {
                    productList.clear();
                    productList.addAll(getProductsResponse.getData());
                    productsListingAdapter.notifyDataSetChanged();
                    if (productList.size() == 0) {
                        lableNodataFound.setVisibility(View.VISIBLE);
                    }

               } else {
                    Helper.alertSnackbar(mContext, ((MainActivity)getActivity()).mToolbar, getProductsResponse.getMessage(), true);
                }

            }

        }
    }

    @Override
    public void apiFailureCallBack(int requestCode, Object response, Object tag) {
        if (customProgressDialog.isShowing()) {
            customProgressDialog.dismiss();
        }
        swipeContainer.setRefreshing(false);
        if (requestCode == REQUEST_CODE_GET_PRODUCTS) {
            if (response instanceof JsonObject) {
                ResponseBody responseBody = (ResponseBody) response;
                Converter<ResponseBody, APIError> errorConverter =
                        ServiceGenerator.getClient().responseBodyConverter(APIError.class, new Annotation[0]);
                // Convert the error body into our Error type.
                try {
                    APIError APIError = errorConverter.convert(responseBody);
                    Helper.alertSnackbar(mContext, ((MainActivity)getActivity()).mToolbar, APIError.getMessage(), true);

                } catch (IOException e) {

                }
            } else {
                Helper.alertSnackbar(mContext, ((MainActivity)getActivity()).mToolbar, getResources().getString(R.string.error_message), true);
            }
        }
    }
}
