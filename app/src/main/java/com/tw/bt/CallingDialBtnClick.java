package com.tw.bt;

import android.view.View;
import android.view.View.OnClickListener;

class CallingDialBtnClick implements OnClickListener {
    final ATBluetoothService atBluetoothService;
    final CallingLayout callingLayout;

    CallingDialBtnClick(CallingLayout tVar, ATBluetoothService aTBluetoothService) {
        this.callingLayout = tVar;
        this.atBluetoothService = aTBluetoothService;
    }

    public void onClick(View view) {
        this.callingLayout.atBluetoothService.twUtil.write(33281, 1, 47);
        // Activate Tab
        this.callingLayout.atBluetoothService.twUtil.sendHandler("ATBluetoothActivity", 65282, R.id.dial);
    }
}
