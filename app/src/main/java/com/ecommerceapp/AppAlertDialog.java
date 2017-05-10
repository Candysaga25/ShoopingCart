package com.ecommerceapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Sagar Shedge on 25/11/16.
 */

public class AppAlertDialog extends Dialog implements View.OnClickListener {

    private final Context context;
    private final IAlertDialogEventListner alertEventListener;
    private Button btn_yes, btn_no;
    private CustomProgressDialog cd;
    private String alertMsg;
    private int requestcode = 9999;

    public AppAlertDialog(Context context, String alertMsg, IAlertDialogEventListner alertEventListener) {
        super(context);
        this.context = context;
        this.alertMsg = alertMsg;
        this.alertEventListener = alertEventListener;
    }

    public AppAlertDialog(int requestcode, Context context, String alertMsg, IAlertDialogEventListner alertEventListener) {
        super(context);
        this.requestcode = requestcode;
        this.context = context;
        this.alertMsg = alertMsg;
        this.alertEventListener = alertEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_alertdailog);
        this.setCancelable(false);
        initview();

    }

    public int getFitnessAppAlertDialogTag() {
        return requestcode;
    }

    public void setFitnessAppAlertDialogMsg(String dialogMsg) {
        alertMsg = dialogMsg;
    }

    private void initview() {
        TextView txt_alertMsg = ((TextView) findViewById(R.id.dialog_msg));
        txt_alertMsg.setText(alertMsg);
        btn_yes = ((Button) findViewById(R.id.dialog_yes_btn));
        btn_no = ((Button) findViewById(R.id.dialog_no_btn));
        btn_no.setOnClickListener(this);
        btn_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_no) {
            alertEventListener.onNoClick(requestcode);
        }
        if (v == btn_yes) {
            alertEventListener.onYesClick(requestcode);
        }
    }
}
