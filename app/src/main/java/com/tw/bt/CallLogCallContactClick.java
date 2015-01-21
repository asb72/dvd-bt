package com.tw.bt;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

class CallLogCallContactClick implements OnClickListener {
    final trListViewItemClick ao;
    final Contact contact;

    CallLogCallContactClick(trListViewItemClick trListViewItemClick, Contact contact) {
        this.ao = trListViewItemClick;
        this.contact = contact;
    }

    public void onClick(DialogInterface dialogInterface, int which) {
        this.ao.atBluetoothActivity.twUtil.a(10, 3, this.contact.number);
    }
}
