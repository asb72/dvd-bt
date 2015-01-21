package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class PairDlgNeutralBtnClick implements OnClickListener {
    private Contact neighbour;
    private ChangeBTNeigbourTypeLongClick d;

    PairDlgNeutralBtnClick(ChangeBTNeigbourTypeLongClick changeBTNeigbourTypeLongClickVar, Contact contact) {
        this.d = changeBTNeigbourTypeLongClickVar;
        this.neighbour = contact;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.d.atBluetoothActivity.twUtil.a(38, 2, this.neighbour.number);
    }
}
