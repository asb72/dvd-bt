package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class DownloadPhonePbBtnClick implements OnClickListener {
    final ATBluetoothActivity m;

    DownloadPhonePbBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.m = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.m.HideTrEdit();
        this.m.twUtil.write(22, 1);
    }
}
