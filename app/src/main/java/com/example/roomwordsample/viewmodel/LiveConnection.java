package com.example.roomwordsample.viewmodel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class LiveConnection extends LiveData<Boolean> {

    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback(){
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            postValue(true);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            postValue(false);
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            postValue(false);
        }
    };

    public LiveConnection(@NonNull Context context){
        this.connectivityManager = context.getSystemService(ConnectivityManager.class);
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(callback);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.unregisterNetworkCallback(callback);
        }
    }
}
