package com.tw.bt;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

class ChangeBTNeigbourTypeLongClick implements OnItemLongClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    ChangeBTNeigbourTypeLongClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
        if (this.atBluetoothActivity.activeTab != R.id.pair) {
            return false;
        }
        Contact neighbour;
        switch (this.atBluetoothActivity.Q) {
            case 0:
                neighbour = (Contact) this.atBluetoothActivity.BTNeighbours.get(i);
                break;
            case 1:
                neighbour = (Contact) this.atBluetoothActivity.btNeighbours.get(i);
                break;
            default:
                neighbour = null;
                break;
        }
        if (neighbour != null) {
            CharSequence charSequence = (neighbour.name == null || neighbour.name.length() == 0) ? neighbour.number : neighbour.name;
            Dialog show = new Builder(this.atBluetoothActivity)
                    .setIcon(R.drawable.pair)
                    .setTitle(charSequence)
                    .setPositiveButton(R.string.alert_dialog_ok, new PairDlgOkBtnClick(this, neighbour))
                    .setNeutralButton(charSequence, new PairDlgNeutralBtnClick(this, neighbour))
                    .setNegativeButton(R.string.alert_dialog_cancel, null)
                    .show();
            LayoutParams attributes = show.getWindow().getAttributes();
            attributes.dimAmount = 0.0f;
            show.getWindow().setAttributes(attributes);
        }
        return true;
    }
}
