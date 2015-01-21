package com.tw.bt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

class CallingLayout {
    private LayoutParams layoutParams;
    private boolean isVisible;
    final ATBluetoothService atBluetoothService;
    private View mView;
    private WindowManager mWindowManager;

    public CallingLayout(ATBluetoothService aTBluetoothService) {
        this.atBluetoothService = aTBluetoothService;
        this.isVisible = false;
        this.mView = ((LayoutInflater) aTBluetoothService.getSystemService("layout_inflater")).inflate(R.layout.calling, null);
        ((ImageView) this.mView.findViewById(R.id.calling_voice)).setOnClickListener(new CallingVoiceBtnClick(this, aTBluetoothService));
        ((ImageView) this.mView.findViewById(R.id.calling_hung)).setOnClickListener(new CallingHungBtnClick(this, aTBluetoothService));
        ((ImageView) this.mView.findViewById(R.id.calling_dial)).setOnClickListener(new CallingDialBtnClick(this, aTBluetoothService));
        this.mWindowManager = (WindowManager) aTBluetoothService.getSystemService("window");
        this.layoutParams = new LayoutParams(502, 66, 0, 0, 2002, 40, 1);
        this.layoutParams.gravity = 49;
    }

    public void hide() {
        if (this.isVisible) {
            this.mWindowManager.removeView(this.mView);
            this.isVisible = false;
        }
    }

    public void show() {
        if (!this.isVisible) {
            this.isVisible = true;
            this.mWindowManager.addView(this.mView, this.layoutParams);
        }
    }
}
