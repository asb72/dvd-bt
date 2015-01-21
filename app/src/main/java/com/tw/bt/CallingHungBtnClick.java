package com.tw.bt;

import android.view.View;
import android.view.View.OnClickListener;

class CallingHungBtnClick implements OnClickListener {
    final ATBluetoothService a;
    final CallingLayout callingLayout;

    CallingHungBtnClick(CallingLayout tVar, ATBluetoothService aTBluetoothService) {
        this.callingLayout = tVar;
        this.a = aTBluetoothService;
    }

    public void onClick(View view) {
        this.callingLayout.atBluetoothService.twUtil.write(10, 0);
    }
}
