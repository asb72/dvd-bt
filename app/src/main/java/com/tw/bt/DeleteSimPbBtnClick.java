package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class DeleteSimPbBtnClick implements OnClickListener {
    final ATBluetoothActivity m;

    DeleteSimPbBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.m = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.m.HideTrEdit();
        this.m.SimPbContactsArray.clear();
        this.m.twUtil.write(22, 128);
        this.m.trListAdapter.notifyDataSetChanged();
    }
}
