package com.ecommerceapp.network;

import android.content.Context;

import com.ecommerceapp.model.GetProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sagar Shedge on 24/11/16.
 */

public class GetProductsService {
    private Context context;
    private Subscriber subscriber;
    public GetProductsService(Context context, Subscriber subscriber)
    {
        this.context = context;
        this.subscriber = subscriber;
    }

    void doInBackground(){
        ServiceGenerator.getClient();
        Call<GetProductsResponse> call = ServiceGenerator.getiWebServices().getProducts();
        call.enqueue(new Callback<GetProductsResponse>() {
            @Override
            public void onResponse(Call<GetProductsResponse> call, Response<GetProductsResponse> response) {

                if (response != null ) {
                    if (Apis.getInstance(context).checkCallbackSubscription(subscriber))
                    {
                        Apis.getInstance(context).unsubscribeCallback(subscriber);

                        if (response.code() == 200 )
                        {
                            subscriber.getCallback().apiSuccessCallBack(subscriber.getRequestCode(), response.body(),null);
                        }
                        else
                        {
                            subscriber.getCallback().apiFailureCallBack(subscriber.getRequestCode(), response.errorBody(),null);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<GetProductsResponse> call, Throwable t) {

                subscriber.getCallback().apiFailureCallBack(subscriber.getRequestCode(),null,null);
            }
        });
    }
}