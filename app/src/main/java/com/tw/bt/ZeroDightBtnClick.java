package com.tw.bt;

import android.view.View;
import android.view.View.OnLongClickListener;

class ZeroDightBtnClick implements OnLongClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    ZeroDightBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public boolean onLongClick(View view) {
        if (this.atBluetoothActivity.callingState == 0) {
            this.atBluetoothActivity.dightTextView.setText(this.atBluetoothActivity.dightTextView.getText() + "+");
        } else {
            this.atBluetoothActivity.twUtil.write(10, 4, 43);
        }
        return true;
    }
}
