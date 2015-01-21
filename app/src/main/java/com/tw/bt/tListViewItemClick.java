package com.tw.bt;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

class tListViewItemClick implements OnItemClickListener {
    final ATBluetoothActivity atBluetoothActivity;
    private Dialog dialog;
    private LayoutParams attributes;

    tListViewItemClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        if (ATBluetoothActivity.ag[position] == 0) {
            View inflate = LayoutInflater.from(this.atBluetoothActivity).inflate(R.layout.alert_dialog_text_entry, null);
            ((TextView) inflate.findViewById(R.id.edit)).setText(this.atBluetoothActivity.settingsValues[position]);
            switch (ATBluetoothActivity.BtnActionNames[position]) {
                case R.string.pin:
                    ((TextView) inflate.findViewById(R.id.edit)).setInputType(8194);
                    break;
            }
            dialog = new Builder(this.atBluetoothActivity).setIcon(R.drawable.setting).setTitle(ATBluetoothActivity.BtnActionNames[position]).setView(inflate).setPositiveButton(R.string.alert_dialog_ok, new SettingsTxtDlgListenerClick(this, inflate, position)).setNegativeButton(R.string.alert_dialog_cancel, null).show();
            attributes = dialog.getWindow().getAttributes();
            attributes.dimAmount = 0.0f;
            dialog.getWindow().setAttributes(attributes);
            return;
        }
        dialog = new Builder(this.atBluetoothActivity).setIcon(R.drawable.setting).setTitle(ATBluetoothActivity.BtnActionNames[position]).setSingleChoiceItems(ATBluetoothActivity.ag[position], this.atBluetoothActivity.af[position], new SettingsSingleChoiceItemDlgClick(this, position)).setNegativeButton(R.string.alert_dialog_cancel, null).show();
        attributes = dialog.getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        dialog.getWindow().setAttributes(attributes);
    }
}
