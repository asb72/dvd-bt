package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class PbCallContactClick implements OnClickListener {
    final Contact an;
    final trListViewItemClick ao;

    PbCallContactClick(trListViewItemClick trListViewItemClick, Contact contact) {
        this.ao = trListViewItemClick;
        this.an = contact;
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        this.ao.atBluetoothActivity.twUtil.a(10, 3, this.an.number);
    }
}
