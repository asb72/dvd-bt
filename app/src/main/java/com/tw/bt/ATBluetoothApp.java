package com.tw.bt;

import android.app.Application;
import android.content.Intent;

public class ATBluetoothApp extends Application {
    public void onCreate() {
        startService(new Intent(this, ATBluetoothService.class));
    }
}
