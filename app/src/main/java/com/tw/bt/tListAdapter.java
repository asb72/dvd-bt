package com.tw.bt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class tListAdapter extends BaseAdapter {
    final ATBluetoothActivity m;
    private Context mContext;

    public tListAdapter(ATBluetoothActivity aTBluetoothActivity, Context context) {
        this.m = aTBluetoothActivity;
        this.mContext = context;
    }

    private View a(ViewGroup parent) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.t_list, parent, false);
        ListItem listItem = new ListItem(this);
        listItem.name = (TextView) inflate.findViewById(R.id.name);
        listItem.value = (TextView) inflate.findViewById(R.id.value);
        inflate.setTag(listItem);
        return inflate;
    }

    private void a(View convertView, int position, ViewGroup parent) {
        ListItem listItem = (ListItem) convertView.getTag();
        listItem.name.setText(ATBluetoothActivity.BtnActionNames[position]);
        listItem.value.setText(this.m.settingsValues[position]);
    }

    public int getCount() {
        return ATBluetoothActivity.BtnActionNames.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = a(parent);
        }
        a(convertView, position, parent);
        return convertView;
    }
}
