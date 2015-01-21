package com.tw.bt;

import android.view.View;
import android.view.View.OnClickListener;

class IncomingCallRejectBtnClick implements OnClickListener {
    final ATBluetoothService a;
    final IncomingCallLayout incomingCallLayout;

    IncomingCallRejectBtnClick(IncomingCallLayout incomingCallLayout, ATBluetoothService aTBluetoothService) {
        this.incomingCallLayout = incomingCallLayout;
        this.a = aTBluetoothService;
    }

    public void onClick(View view) {
        this.incomingCallLayout.atBluetoothService.twUtil.write(10, 2);
        this.incomingCallLayout.hide();
    }
}
