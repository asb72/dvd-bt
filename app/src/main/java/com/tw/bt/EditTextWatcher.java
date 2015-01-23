package com.tw.bt;

import android.os.AsyncTask.Status;
import android.text.Editable;
import android.text.TextWatcher;

class EditTextWatcher implements TextWatcher {
    final ATBluetoothActivity atBluetoothActivity;

    EditTextWatcher(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if(this.atBluetoothActivity.last_search_str.equals(charSequence.toString()))
            return;
        else
            this.atBluetoothActivity.last_search_str = charSequence.toString();

        if (!(this.atBluetoothActivity.searchContactsInBackgroundTask == null || this.atBluetoothActivity.searchContactsInBackgroundTask.getStatus() == Status.FINISHED)) {
            this.atBluetoothActivity.searchContactsInBackgroundTask.cancel(true);
        }
        this.atBluetoothActivity.searchContactsInBackgroundTask = (SearchContactsInBackgroundTask) new SearchContactsInBackgroundTask(this.atBluetoothActivity).execute(new CharSequence[]{charSequence});
    }
}
