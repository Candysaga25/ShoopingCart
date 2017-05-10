package com.ecommerceapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

/**
 * Created by ADMIN on 3/8/2016.
 */
public class CustomProgressDialog extends AlertDialog {

    private  String message;

    public CustomProgressDialog(Context context, String msg, Boolean cancleable) {
        super(context);
        this.message = msg;
        this.setCancelable(cancleable);

    }
    public CustomProgressDialog(Context context, Boolean cancleable) {
        super(context);
        this.setCancelable(cancleable);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_progressdialog);
        //noinspection ConstantConditions
        if(message != null){
        ((TextView) findViewById(R.id.dialog_msg)).setText(message);
        }
    }
}
