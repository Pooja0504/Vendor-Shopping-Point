package com.shopping_point.vendor_shopping_point.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.shopping_point.vendor_shopping_point.utils.OnNetworkListener;

import static com.shopping_point.vendor_shopping_point.utils.InternetUtils.isNetworkConnected;


public class NetworkChangeReceiver extends BroadcastReceiver {

    OnNetworkListener onNetworkListener;

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)) {
            onNetworkListener.onNetworkDisconnected();
        } else {
            onNetworkListener.onNetworkConnected();
        }
    }


}
