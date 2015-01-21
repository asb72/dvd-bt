package com.tw.bt;

import android.widget.ImageView;
import android.widget.TextView;

class trList {
    TextView number;
    ImageView btImage;
    final trListAdapter am;
    TextView name;

    public trList(trListAdapter trListAdapter) {
        this.am = trListAdapter;
    }
}
