package com.tw.bt;

import android.view.View;
import android.view.View.OnLongClickListener;

class BackSpaceBtnClick implements OnLongClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    BackSpaceBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public boolean onLongClick(View view) {
        if (this.atBluetoothActivity.callingState == 0) {
            this.atBluetoothActivity.dightTextView.setText("");
        }
        return true;
    }
}
