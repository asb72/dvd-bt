package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class DeletePhonePbBtnClick implements OnClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    DeletePhonePbBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.atBluetoothActivity.HideTrEdit();
        this.atBluetoothActivity.PbContactsArray.clear();
        this.atBluetoothActivity.twUtil.write(22, 0);
        this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
    }
}
