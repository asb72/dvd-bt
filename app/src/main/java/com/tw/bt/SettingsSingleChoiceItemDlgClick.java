package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class SettingsSingleChoiceItemDlgClick implements OnClickListener {
    final int aq;
    final tListViewItemClick ar;

    SettingsSingleChoiceItemDlgClick(tListViewItemClick tListViewItemClickVar, int i) {
        this.ar = tListViewItemClickVar;
        this.aq = i;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        this.ar.atBluetoothActivity.af[this.aq] = i;
        this.ar.atBluetoothActivity.settingsValues[this.aq] = this.ar.atBluetoothActivity.getResources().getStringArray(ATBluetoothActivity.ag[this.aq])[this.ar.atBluetoothActivity.af[this.aq]];
        switch (ATBluetoothActivity.BtnActionNames[this.aq]) {
            case R.string.auto_connect:
                this.ar.atBluetoothActivity.twUtil.write(36, this.ar.atBluetoothActivity.af[this.aq]);
                break;
            case R.string.auto_answer:
                this.ar.atBluetoothActivity.twUtil.write(37, this.ar.atBluetoothActivity.af[this.aq]);
                break;
        }
        this.ar.atBluetoothActivity.tListAdapter.notifyDataSetChanged();
    }
}
