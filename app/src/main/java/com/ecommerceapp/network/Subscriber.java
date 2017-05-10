package com.ecommerceapp.network;

/**
 * Created by Sagar Shedge on 14/11/16.
 */
public class Subscriber {
    public Subscriber(Apis.ApisCallbackInterface apisCallbackInterface, int requestCode) {
        this.apisCallbackInterface = apisCallbackInterface;
        this.requestCode = requestCode;
    }

    private Apis.ApisCallbackInterface apisCallbackInterface;
    private int requestCode;

    public int getRequestCode() {
        return requestCode;
    }

    public Apis.ApisCallbackInterface getCallback() {
        return apisCallbackInterface;
    }
}
