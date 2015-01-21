package com.tw.bt;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

class trListViewItemClick implements OnItemClickListener {
    final ATBluetoothActivity atBluetoothActivity;

    trListViewItemClick(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        Contact contact;
        Builder icon;
        CharSequence charSequence;
        Dialog show;
        LayoutParams attributes;
        switch (this.atBluetoothActivity.activeTab) {
            case R.id.calllog:
                switch (this.atBluetoothActivity.currentCallog) {
                    case 0:
                        contact = (Contact) this.atBluetoothActivity.callogMissed.get(position);
                        break;
                    case 1:
                        contact = (Contact) this.atBluetoothActivity.callogAnswered.get(position);
                        break;
                    case 2:
                        contact = (Contact) this.atBluetoothActivity.callogOutgoing.get(position);
                        break;
                    default:
                        contact = null;
                        break;
                }
                if (contact != null) {
                    icon = new Builder(this.atBluetoothActivity).setIcon(R.drawable.call);
                    charSequence = (contact.name == null || contact.name.length() == 0) ? contact.number : contact.name;
                    show = icon
                            .setTitle(charSequence)
                            .setPositiveButton(R.string.alert_dialog_ok, new CallLogCallContactClick(this, contact))
                            .setNegativeButton(R.string.alert_dialog_cancel, null)
                            .show();
                    attributes = show.getWindow().getAttributes();
                    attributes.dimAmount = 0.0f;
                    show.getWindow().setAttributes(attributes);
                }
                break;
            case R.id.phonebook:
                switch (this.atBluetoothActivity.isSimPhonebookActive) {
                    case 0:
                        contact = (
                                this.atBluetoothActivity.pbSearchResult == null
                                || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE
                                || this.atBluetoothActivity.trEdit.getText().length() <= 0
                                )?
                                    (Contact) this.atBluetoothActivity.PbContactsArray.get(position)
                                    : (Contact) this.atBluetoothActivity.pbSearchResult.get(position);
                        break;
                    case 1:
                        contact = (
                                this.atBluetoothActivity.simPbSearchResult == null
                                || this.atBluetoothActivity.trEdit.getVisibility() != View.VISIBLE
                                || this.atBluetoothActivity.trEdit.getText().length() <= 0
                                ) ?
                                    (Contact) this.atBluetoothActivity.SimPbContactsArray.get(position)
                                    : (Contact) this.atBluetoothActivity.simPbSearchResult.get(position);
                        break;
                    default:
                        contact = null;
                        break;
                }
                if (contact != null) {
                    icon = new Builder(this.atBluetoothActivity).setIcon(R.drawable.call);
                    charSequence = (contact.name == null || contact.name.length() == 0) ? contact.number : contact.name;
                    show = icon
                            .setTitle(charSequence)
                            .setPositiveButton(R.string.alert_dialog_ok, new PbCallContactClick(this, contact))
                            .setNegativeButton(R.string.alert_dialog_cancel, null)
                            .show();
                    attributes = show.getWindow().getAttributes();
                    attributes.dimAmount = 0.0f;
                    show.getWindow().setAttributes(attributes);
                }
                break;
            case R.id.pair:
                switch (this.atBluetoothActivity.Q) {
                    case 0:
                        this.atBluetoothActivity.twUtil.a(6, 1, ((Contact) this.atBluetoothActivity.BTNeighbours.get(position)).number);
                        break;
                    case 1:
                        this.atBluetoothActivity.twUtil.write(28, position + 1);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
