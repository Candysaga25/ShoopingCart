package com.ecommerceapp.network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sagar Shedge on 14/11/16.
 */
public class Apis {
    private static Apis ourInstance = new Apis();

    public static Apis getInstance(Context context) {
        return ourInstance;
    }
    private ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();
    private Context context;
    private Apis() {
    }


    public void getProducts(ApisCallbackInterface callBackInterface , int requestCode){
        Subscriber subscriber = new Subscriber(callBackInterface, requestCode);
        subscribeCallback(subscriber);
        GetProductsService getProductsService = new GetProductsService(context,subscriber);
        getProductsService.doInBackground();
    }
    public interface ApisCallbackInterface
    {
        void apiSuccessCallBack(int requestCode, Object response, Object tag);

        void apiFailureCallBack(int requestCode, Object response, Object tag);
    }

    public void clearSubscriberList()
    {
        subscribers.clear();
    }

    private void subscribeCallback(Subscriber subscriber)
    {
        if (subscriber != null)
        {
            subscribers.add(subscriber);
            Log.e("Subscriber Count", subscribers.size() + "");
        }
        else
        {
            Log.e("subscribeCallback", "Subscriber is null");
        }
    }

    public boolean checkCallbackSubscription(ApisCallbackInterface callbackInterface, int requestCode)
    {
        if(callbackInterface!=null)
        {
            for(Subscriber subscriber:subscribers)
            {
                if((subscriber.getCallback().equals(callbackInterface))
                        && (subscriber.getRequestCode() == requestCode))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean checkCallbackSubscription(ApisCallbackInterface callbackInterface)
    {
        if(callbackInterface!=null)
        {
            for(Subscriber subscriber:subscribers)
            {
                if ((subscriber.getCallback().equals(callbackInterface)))
                    return true;
            }
        }

        return false;
    }

    public boolean checkCallbackSubscription(Subscriber subscriber)
    {
        if (subscriber != null)
        {
            return subscribers.contains(subscriber);
        }
        else
        {
            Log.e("checkCallbackSubscriptn", "Subscriber is null");
            return false;
        }
    }

    public void unsubscribeCallback(Subscriber subscriber)
    {
        if (subscriber != null)
        {
            /*if (subscriber.getTask().getStatus() == AsyncTask.Status.RUNNING)
            {
                subscriber.getTask().cancel(true);
            }*/

            subscribers.remove(subscriber);
            Log.e("Subscriber Count", subscribers.size() + "");
        }
        else
        {
            Log.e("unsubscribeCallback", "Subscriber is null");
        }
    }

    public void unsubscribeCallback(ApisCallbackInterface callbackInterface,int requestCode)
    {
        if (callbackInterface != null)
        {
            for (int i = 0; i < subscribers.size(); i++)
            {
                Subscriber subscriber = subscribers.get(i);

                if (subscriber.getCallback().equals(callbackInterface) && subscriber.getRequestCode() == requestCode)
                {
                    unsubscribeCallback(subscriber);
                }
            }
        }
        else
        {
            Log.e("unsubscribeAllCallbacks", "callbackInterface is null");
        }
    }

    public void unsubscribeAllCallbacks(ApisCallbackInterface callbackInterface)
    {
        if (callbackInterface != null)
        {
            for (int i = 0; i < subscribers.size(); i++)
            {
                Subscriber subscriber = subscribers.get(i);

                if (subscriber.getCallback().equals(callbackInterface))
                {
                    unsubscribeCallback(subscriber);
                }
            }
        }
        else
        {
            Log.e("unsubscribeAllCallbacks", "callbackInterface is null");
        }
    }

    public void takeErrorAction(Subscriber subscriber)
    {
        try
        {
            Log.e("Api Request Error", "Request Code : " + subscriber.getRequestCode() + ", ClassName :" + subscriber.getCallback().getClass().getSimpleName());
            unsubscribeCallback(subscriber);
        }
        catch (NullPointerException e)
        {
            Log.e("Api Request Error", "Request Code is null or callbackInterface is null");
        }
    }
}


