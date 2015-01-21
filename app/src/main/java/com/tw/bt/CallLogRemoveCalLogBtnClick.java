package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class CallLogRemoveCalLogBtnClick implements OnClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    CallLogRemoveCalLogBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.atBluetoothActivity.callogMissed.clear();
        this.atBluetoothActivity.callogAnswered.clear();
        this.atBluetoothActivity.callogOutgoing.clear();
        this.atBluetoothActivity.twUtil.write(26, 0);
        this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
    }
}
