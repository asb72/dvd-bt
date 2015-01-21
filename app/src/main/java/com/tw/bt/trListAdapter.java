package com.tw.bt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class trListAdapter extends BaseAdapter {
    final ATBluetoothActivity atBluetoothActivity;
    private Context mContext;

    public trListAdapter(ATBluetoothActivity aTBluetoothActivity, Context context) {
        this.atBluetoothActivity = aTBluetoothActivity;
        this.mContext = context;
    }

    private View a(ViewGroup parent) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.tr_list, parent, false);
        trList trList = new trList(this);
        trList.name = (TextView) inflate.findViewById(R.id.name);
        trList.number = (TextView) inflate.findViewById(R.id.number);
        trList.btImage = (ImageView) inflate.findViewById(R.id.bt);
        inflate.setTag(trList);
        return inflate;
    }

    private void a(View convertView, int position, ViewGroup parent) {
        Contact neighbour = null;
        trList trList = (trList) convertView.getTag();
        switch (this.atBluetoothActivity.activeTab) {
            case R.id.calllog:
                switch (this.atBluetoothActivity.currentCallog) {
                    case 0:
                        neighbour = (Contact) this.atBluetoothActivity.callogMissed.get(position);
                        break;
                    case 1:
                        neighbour = (Contact) this.atBluetoothActivity.callogAnswered.get(position);
                        break;
                    case 2:
                        neighbour = (Contact) this.atBluetoothActivity.callogOutgoing.get(position);
                        break;
                }
                if (neighbour != null) {
                    if (neighbour.name == null || neighbour.name.length() == 0) {
                        trList.name.setText(neighbour.number);
                        trList.number.setVisibility(View.INVISIBLE);
                    } else {
                        trList.name.setText(neighbour.name);
                        trList.number.setText(neighbour.number);
                        trList.number.setVisibility(View.VISIBLE);
                    }
                    trList.btImage.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.phonebook:
                switch (this.atBluetoothActivity.isSimPhonebookActive) {
                    case 0:
                        neighbour = (this.atBluetoothActivity.pbSearchResult == null || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE || this.atBluetoothActivity.trEdit.getText().length() <= 0) ? (Contact) this.atBluetoothActivity.PbContactsArray.get(position) : (Contact) this.atBluetoothActivity.pbSearchResult.get(position);
                        break;
                    case 1:
                        neighbour = (this.atBluetoothActivity.simPbSearchResult == null || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE || this.atBluetoothActivity.trEdit.getText().length() <= 0) ? (Contact) this.atBluetoothActivity.SimPbContactsArray.get(position) : (Contact) this.atBluetoothActivity.simPbSearchResult.get(position);
                        break;
                }
                if (neighbour != null) {
                    if (neighbour.name == null || neighbour.name.length() == 0) {
                        trList.name.setText(neighbour.number);
                        trList.number.setVisibility(View.INVISIBLE);
                    } else {
                        trList.name.setText(neighbour.name);
                        trList.number.setText(neighbour.number);
                        trList.number.setVisibility(View.VISIBLE);
                    }
                    trList.btImage.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.pair:
                switch (this.atBluetoothActivity.Q) {
                    case 0:
                        neighbour = (Contact) this.atBluetoothActivity.BTNeighbours.get(position);
                        if (neighbour != null) {
                            if (neighbour.name == null || neighbour.name.length() == 0) {
                                trList.name.setText(neighbour.number);
                            } else {
                                trList.name.setText(neighbour.name);
                            }
                            trList.number.setVisibility(View.INVISIBLE);
                            if (neighbour.number.equals(this.atBluetoothActivity.CurrentPairedBtNeighbour)) {
                                trList.btImage.setVisibility(View.VISIBLE);
                            } else {
                                trList.btImage.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;
                    case 1:
                        neighbour = (Contact) this.atBluetoothActivity.btNeighbours.get(position);
                        if (neighbour != null) {
                            if (neighbour.name == null || neighbour.name.length() == 0) {
                                trList.name.setText(neighbour.number);
                            } else {
                                trList.name.setText(neighbour.name);
                            }
                            trList.number.setVisibility(View.INVISIBLE);
                            if (neighbour.number.equals(this.atBluetoothActivity.CurrentPairedBtNeighbour)) {
                                trList.btImage.setVisibility(View.VISIBLE);
                            } else {
                                trList.btImage.setVisibility(View.INVISIBLE);
                            }
                        }
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
    }

    public int getCount() {
        switch (this.atBluetoothActivity.activeTab) {
            case R.id.calllog:
                switch (this.atBluetoothActivity.currentCallog) {
                    case 0:
                        return this.atBluetoothActivity.callogMissed.size();
                    case 1:
                        return this.atBluetoothActivity.callogAnswered.size();
                    case 2:
                        return this.atBluetoothActivity.callogOutgoing.size();
                    default:
                        break;
                }
                break;
            case R.id.phonebook:
                switch (this.atBluetoothActivity.isSimPhonebookActive) {
                    case 0:
                        return (this.atBluetoothActivity.pbSearchResult == null || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE || this.atBluetoothActivity.trEdit.getText().length() <= 0) ? this.atBluetoothActivity.PbContactsArray.size() : this.atBluetoothActivity.pbSearchResult.size();
                    case 1:
                        return (this.atBluetoothActivity.simPbSearchResult == null || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE || this.atBluetoothActivity.trEdit.getText().length() <= 0) ? this.atBluetoothActivity.SimPbContactsArray.size() : this.atBluetoothActivity.simPbSearchResult.size();
                    default:
                        break;
                }
                break;
            case R.id.pair:
                switch (this.atBluetoothActivity.Q) {
                    case 0:
                        return this.atBluetoothActivity.BTNeighbours.size();
                    case 1:
                        return this.atBluetoothActivity.btNeighbours.size();
                    default:
                        break;
                }
                break;
        }
        return 0;
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
