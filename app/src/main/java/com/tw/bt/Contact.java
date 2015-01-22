package com.tw.bt;

/* Used as BtPeer and Contacts items */
class Contact {
    String number;
    String nameConverted;
    final ATBluetoothActivity atBluetoothActivity;
    String name;

    public Contact(ATBluetoothActivity aTBluetoothActivity, String str, String str2, String str3) {
        this.atBluetoothActivity = aTBluetoothActivity;
        this.number = str;
        this.name = str2;
        this.nameConverted = str3;
    }

    public String getNumber() {
        return number;
    }

    public String getNameConverted() {
        return nameConverted;
    }

    public String getName() {
        return name;
    }
}
