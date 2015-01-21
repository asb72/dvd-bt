package com.tw.bt;

import android.widget.TextView;

class ListItem {
    TextView name;
    TextView value;
    final tListAdapter tListAdapter;

    public ListItem(tListAdapter tListAdapter) {
        this.tListAdapter = tListAdapter;
    }
}
