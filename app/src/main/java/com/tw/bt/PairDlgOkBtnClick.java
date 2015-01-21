package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class PairDlgOkBtnClick implements OnClickListener {
    final Contact neighbour;
    final ChangeBTNeigbourTypeLongClick d;

    PairDlgOkBtnClick(ChangeBTNeigbourTypeLongClick changeBTNeigbourTypeLongClickVar, Contact contact) {
        this.d = changeBTNeigbourTypeLongClickVar;
        this.neighbour = contact;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.d.atBluetoothActivity.twUtil.a(38, 1, this.neighbour.number);
    }
}
