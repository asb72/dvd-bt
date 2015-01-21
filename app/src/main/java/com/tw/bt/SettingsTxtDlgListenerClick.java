package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.TextView;

class SettingsTxtDlgListenerClick implements OnClickListener {
    final int btnNumber;
    final tListViewItemClick ar;
    final View view;

    SettingsTxtDlgListenerClick(tListViewItemClick tListViewItemClickVar, View view, int i) {
        this.ar = tListViewItemClickVar;
        this.view = view;
        this.btnNumber = i;
    }

    public void onClick(DialogInterface dialogInterface, int which) {
        String value = ((TextView) this.view.findViewById(R.id.edit)).getText().toString();
        if (ATBluetoothActivity.BtnActionNames[this.btnNumber] == R.string.device_name) {
            if ("Topway".equals(value)) {
                this.ar.atBluetoothActivity.twUtil.a(44, this.ar.atBluetoothActivity.settingsValues[this.btnNumber]);
                return;
            } else if ("TopwayUp".equals(value)) {
                this.ar.atBluetoothActivity.twUtil.write(49);
                return;
            }
        }
        if (ATBluetoothActivity.BtnActionNames[this.btnNumber] == R.string.device_name && "Topway".equals(value)) {
            this.ar.atBluetoothActivity.twUtil.a(44, this.ar.atBluetoothActivity.settingsValues[this.btnNumber]);
            return;
        }
        this.ar.atBluetoothActivity.settingsValues[this.btnNumber] = value;
        switch (ATBluetoothActivity.BtnActionNames[this.btnNumber]) {
            case R.string.device_name:
                this.ar.atBluetoothActivity.twUtil.a(30, this.ar.atBluetoothActivity.settingsValues[this.btnNumber]);
                break;
            case R.string.pin:
                this.ar.atBluetoothActivity.twUtil.a(32, this.ar.atBluetoothActivity.settingsValues[this.btnNumber]);
                break;
        }
        this.ar.atBluetoothActivity.tListAdapter.notifyDataSetChanged();
    }
}
