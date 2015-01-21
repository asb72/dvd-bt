package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class DownloadSimPbBtnClick implements OnClickListener {
    final ATBluetoothActivity m;

    DownloadSimPbBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.m = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.m.HideTrEdit();
        this.m.twUtil.write(22, 129);
    }
}
