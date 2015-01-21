package com.tw.bt;

import android.app.AlertDialog.Builder;
import android.os.Handler;
import android.os.Message;
import android.source.PinyinConv;
import android.tw.john.TWUtil.TWObject;
import android.util.Log;
import android.view.View;

class ATBluetoothActivityHandler extends Handler {
    final ATBluetoothActivity atBluetoothActivity;

    ATBluetoothActivityHandler(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void handleMessage(Message message) {
        try {
            String str;
            String str2;
            if (message.obj instanceof TWObject) {
                TWObject tWObject = (TWObject) message.obj;
                str = (String) tWObject.obj3;
                str2 = (String) tWObject.obj4;
            } else {
                str = (String) message.obj;
                str2 = null;
            }
            switch (message.what) {
                case 4: // Receive BT neighbours
                    if (message.arg1 == 0) {
                        if (!(str == null || str.equals(""))) {
                            this.atBluetoothActivity.CurrentPairedBtNeighbour = str;
                        }
                        this.atBluetoothActivity.twUtil.write(3, 2);
                        if (this.atBluetoothActivity.CurrentPairedBtNeighbour != null) {
                            this.atBluetoothActivity.twUtil.write(22, 255);
                            this.atBluetoothActivity.twUtil.write(22, 127);
                            this.atBluetoothActivity.twUtil.write(26, 255);
                        }
                    } else {
                        if (message.arg1 == 1) {
                            this.atBluetoothActivity.BTNeighbours.clear();
                            if (this.atBluetoothActivity.t == 2 && this.atBluetoothActivity.CurrentPairedBtNeighbour == null) {
                                this.atBluetoothActivity.CurrentPairedBtNeighbour = str;
                                if (this.atBluetoothActivity.CurrentPairedBtNeighbour != null) {
                                    this.atBluetoothActivity.twUtil.write(22, 255);
                                    this.atBluetoothActivity.twUtil.write(22, 127);
                                    this.atBluetoothActivity.twUtil.write(26, 255);
                                }
                            }
                        }
                        this.atBluetoothActivity.BTNeighbours.add(new Contact(this.atBluetoothActivity, str, str2, null));
                    }
                    this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                    break;
                case 7:
                    if (this.atBluetoothActivity.t != message.arg1) {
                        this.atBluetoothActivity.t = message.arg1;
                        if (this.atBluetoothActivity.t == 2) {
                            this.atBluetoothActivity.twUtil.write(3, 1);
                            return;
                        }
                        this.atBluetoothActivity.callingState = 0;
                        this.atBluetoothActivity.dightTextView.setText("");
                        this.atBluetoothActivity.CurrentPairedBtNeighbour = null;
                        this.atBluetoothActivity.PbContactsArray.clear();
                        this.atBluetoothActivity.SimPbContactsArray.clear();
                        this.atBluetoothActivity.callogMissed.clear();
                        this.atBluetoothActivity.callogAnswered.clear();
                        this.atBluetoothActivity.callogOutgoing.clear();
                        this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                        switch (this.atBluetoothActivity.activeTab) {
                            case R.id.dial:
                            case R.id.calllog:
                            case R.id.phonebook:
                            case R.id.a2dp:
                                this.atBluetoothActivity.activateTab(R.id.pair);
                                break;
                        }
                        this.atBluetoothActivity.twUtil.write(3, 2);
                    }
                    break;
                case 9:
                    int i;
                    switch (message.arg1) {
                        case 0:
                        case 1:
                            i = 0;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            i = 2;
                            break;
                        default:
                            i = 0;
                            break;
                    }
                    if (this.atBluetoothActivity.t != i) {
                        this.atBluetoothActivity.t = i;
                        if (this.atBluetoothActivity.t == 2) {
                            this.atBluetoothActivity.twUtil.write(3, 1);
                            return;
                        }
                        this.atBluetoothActivity.callingState = 0;
                        this.atBluetoothActivity.dightTextView.setText("");
                        this.atBluetoothActivity.CurrentPairedBtNeighbour = null;
                        this.atBluetoothActivity.PbContactsArray.clear();
                        this.atBluetoothActivity.SimPbContactsArray.clear();
                        this.atBluetoothActivity.callogMissed.clear();
                        this.atBluetoothActivity.callogAnswered.clear();
                        this.atBluetoothActivity.callogOutgoing.clear();
                        this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                        switch (this.atBluetoothActivity.activeTab) {
                            case R.id.dial:
                            case R.id.calllog:
                            case R.id.phonebook:
                            case R.id.a2dp:
                                this.atBluetoothActivity.activateTab(R.id.pair);
                                break;
                        }
                        this.atBluetoothActivity.twUtil.write(3, 2);
                    }
                    break;
                case 11:
                    if (this.atBluetoothActivity.callingState != message.arg1) {
                        this.atBluetoothActivity.callingState = message.arg1;
                        if (this.atBluetoothActivity.callingState != 0 && str != null) {
                            this.atBluetoothActivity.telNumberStr = str;
                            this.atBluetoothActivity.telContactStr = str2;
                        } else if (this.atBluetoothActivity.callingState == 0) {
                            this.atBluetoothActivity.telNumberStr = null;
                            this.atBluetoothActivity.telContactStr = null;
                        }
                        if (this.atBluetoothActivity.callingState == 0) {
                            this.atBluetoothActivity.dightTextView.setText("");
                        } else {
                            CharSequence contact = (this.atBluetoothActivity.telContactStr == null || this.atBluetoothActivity.telContactStr.length() == 0)
                                    ? this.atBluetoothActivity.telNumberStr : this.atBluetoothActivity.telContactStr;
                            switch (this.atBluetoothActivity.callingState) {
                                case 3:
                                case 4:
                                    contact = contact + "...";
                                    break;
                            }
                            this.atBluetoothActivity.dightTextView.setText(contact);
                        }
                        switch (this.atBluetoothActivity.callingState) {
                            case 0:
                                if (this.atBluetoothActivity.lastActiveTab != -1) {
                                    this.atBluetoothActivity.activateTab(this.atBluetoothActivity.lastActiveTab);
                                    this.atBluetoothActivity.lastActiveTab = -1;
                                }
                                break;
                            case 1:
                            case 4:
                                if (this.atBluetoothActivity.lastActiveTab == -1 && this.atBluetoothActivity.activeTab != R.id.dial) {
                                    this.atBluetoothActivity.lastActiveTab = this.atBluetoothActivity.activeTab;
                                    this.atBluetoothActivity.activateTab(R.id.dial);
                                }
                                break;
                        }
                        if (this.atBluetoothActivity.activeTab == R.id.dial) {
                            // HideCallingLayout
                            this.atBluetoothActivity.twUtil.sendHandler("ATBluetoothService", 65281);
                        } else {
                            // CallingLayoutManipulations
                            this.atBluetoothActivity.twUtil.sendHandler("ATBluetoothService", 65280);
                        }
                    }
                    break;
                case 23:
                    this.atBluetoothActivity.S = message.arg1;
                    if (this.atBluetoothActivity.S != 1) {
                        switch (this.atBluetoothActivity.isSimPhonebookActive) {
                            case 0:
                                this.atBluetoothActivity.mToast
                                        .setText(this.atBluetoothActivity.getString(R.string.total) + " " + this.atBluetoothActivity.PbContactsArray.size() + " " + this.atBluetoothActivity.getString(R.string.record));
                                this.atBluetoothActivity.mToast.show();
                                break;
                            case 1:
                                this.atBluetoothActivity.mToast
                                        .setText(this.atBluetoothActivity.getString(R.string.total) + " " + this.atBluetoothActivity.SimPbContactsArray.size() + " " + this.atBluetoothActivity.getString(R.string.record));
                                this.atBluetoothActivity.mToast.show();
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 24: //Receive contacts
                    // message.arg1 - contact ID
                    switch ((message.arg1 >> 24) & 255) {
                        case 0:
                            if ((message.arg1 & 16777215) == 0) {
                                this.atBluetoothActivity.PbContactsArray.clear();
                            }
                            this.atBluetoothActivity.PbContactsArray.add(new Contact(this.atBluetoothActivity, str, str2, PinyinConv.cn2py(str2)));
                            this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                            if (this.atBluetoothActivity.S == 1) {
                                this.atBluetoothActivity.mToast
                                        .setText(this.atBluetoothActivity.PbContactsArray.size() + " " + this.atBluetoothActivity.getString(R.string.record));
                                this.atBluetoothActivity.mToast.show();
                            }
                            break;
                        case 1:
                            if ((message.arg1 & 16777215) == 0) {
                                this.atBluetoothActivity.SimPbContactsArray.clear();
                            }
                            this.atBluetoothActivity.SimPbContactsArray.add(new Contact(this.atBluetoothActivity, str, str2, PinyinConv.cn2py(str2)));
                            this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                            if (this.atBluetoothActivity.S == 1) {
                                this.atBluetoothActivity.mToast
                                        .setText(this.atBluetoothActivity.SimPbContactsArray.size() + " " + this.atBluetoothActivity.getString(R.string.record));
                                this.atBluetoothActivity.mToast.show();
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 27: //Receive callog
                    if ((message.arg1 & 16777215) == 0) {
                        this.atBluetoothActivity.callogMissed.clear();
                        this.atBluetoothActivity.callogAnswered.clear();
                        this.atBluetoothActivity.callogOutgoing.clear();
                    }
                    int i2 = (message.arg1 >> 24) & 255;
                    if ((i2 & 1) != 1) {
                        this.atBluetoothActivity.callogOutgoing.add(0, new Contact(this.atBluetoothActivity, str, str2, null));
                    } else if ((i2 & 2) == 2) {
                        this.atBluetoothActivity.callogAnswered.add(0, new Contact(this.atBluetoothActivity, str, str2, null));
                    } else {
                        this.atBluetoothActivity.callogMissed.add(0, new Contact(this.atBluetoothActivity, str, str2, null));
                    }
                    this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                    break;
                case 29: // BT neighbour search result
                    if (message.arg1 == 0) { // nothing found
                        this.atBluetoothActivity.progressBar.setVisibility(View.INVISIBLE);
                        return;
                    }
                    if (message.arg1 == 1) {
                        this.atBluetoothActivity.btNeighbours.clear();
                    }
                    this.atBluetoothActivity.btNeighbours.add(new Contact(this.atBluetoothActivity, str, str2, null));
                    this.atBluetoothActivity.trListAdapter.notifyDataSetChanged();
                    break;
                case 31: //Receive settings BT name
                    this.atBluetoothActivity.settingsValues[0] = str;
                    this.atBluetoothActivity.tListAdapter.notifyDataSetChanged();
                    break;
                case 33: //Receive settings BT password
                    this.atBluetoothActivity.settingsValues[1] = str;
                    this.atBluetoothActivity.tListAdapter.notifyDataSetChanged();
                    break;
                case 35: //Receive settings
                    this.atBluetoothActivity.af[2] = message.arg1 & 255;
                    this.atBluetoothActivity.af[3] = (message.arg1 >> 8) & 255;
                    this.atBluetoothActivity.settingsValues[2] = this.atBluetoothActivity.getResources().getStringArray(ATBluetoothActivity.ag[2])[this.atBluetoothActivity.af[2]];
                    this.atBluetoothActivity.settingsValues[3] = this.atBluetoothActivity.getResources().getStringArray(ATBluetoothActivity.ag[3])[this.atBluetoothActivity.af[3]];
                    this.atBluetoothActivity.tListAdapter.notifyDataSetChanged();
                    break;
                case 45: // Receive BT module version
                    new Builder(this.atBluetoothActivity)
                            .setIcon(R.drawable.setting)
                            .setTitle(str)
                            .setNegativeButton(R.string.alert_dialog_cancel, null)
                            .show();
                    break;
                case 48:
                    this.atBluetoothActivity.mToast.setText(message.arg1 == 1 ? R.string.pair_s : R.string.pair_f);
                    this.atBluetoothActivity.mToast.show();
                    break;
                case 50: // BT module update
                    this.atBluetoothActivity.mToast.setText(R.string.up_s);
                    this.atBluetoothActivity.mToast.show();
                    break;
                case 65282:
                    this.atBluetoothActivity.activateTab(message.arg1);
                    break;
                default:
                    break;
            }
        } catch (Throwable e) {
            Log.e("ATBluetoothActivity", Log.getStackTraceString(e));
        }
    }
}
