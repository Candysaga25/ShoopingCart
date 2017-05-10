package com.ecommerceapp.network;

import com.ecommerceapp.AppConstant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sagar Shedge on 4/10/16.
 */
public class ServiceGenerator {

    public static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder = null;
    private static IWebServices iWebServices = null;

    public static Retrofit getClient() {
       /* Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

        if (retrofit==null) {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            httpClient.addInterceptor(logging);
            Retrofit.Builder builder =
                    new Retrofit.Builder()
                            .baseUrl(AppConstant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create());
            retrofit = builder.client(httpClient.build()).build();
            iWebServices =  retrofit.create(IWebServices.class);


        }
        return retrofit;
    }

    public static IWebServices getiWebServices() {
        return iWebServices;
    }

    public static void setiWebServices(IWebServices iWebServices) {
        ServiceGenerator.iWebServices = iWebServices;
    }
}
