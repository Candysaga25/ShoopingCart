package com.ecommerceapp.network;

import com.ecommerceapp.model.GetProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Sagar Shedge on 4/10/16.
 */
public interface IWebServices {

    @GET("products")
    Call<GetProductsResponse> getProducts();

}
