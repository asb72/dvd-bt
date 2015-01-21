package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class DeleteAllPairRecordsBtnClick implements OnClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    DeleteAllPairRecordsBtnClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.atBluetoothActivity.BTNeighbours.clear();
        this.atBluetoothActivity.twUtil.write(5);
        this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
    }
}
