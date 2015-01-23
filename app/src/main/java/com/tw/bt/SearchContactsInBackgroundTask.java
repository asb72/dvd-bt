package com.tw.bt;

import android.os.AsyncTask;

import java.util.ArrayList;

class SearchContactsInBackgroundTask extends AsyncTask {
    final ATBluetoothActivity atBluetoothActivity;

    public SearchContactsInBackgroundTask(ATBluetoothActivity aTBluetoothActivity) {
        this.atBluetoothActivity = aTBluetoothActivity;
    }

    protected ArrayList a(CharSequence... charSequenceArr) {
        int i = 0;
        if (isCancelled()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        CharSequence charSequence = charSequenceArr[0];
        if (charSequence != null && charSequence.length() > 0) {
            ArrayList p;
            p = new ArrayList();
            CharSequence searchStr = charSequence.toString().toUpperCase();

            switch (ATBluetoothActivity.GetActivePb(this.atBluetoothActivity)) {
                case 0:
                    p = ATBluetoothActivity.GetPbContactsArray(this.atBluetoothActivity);
                    break;
                case 1:
                    p = ATBluetoothActivity.GetSimPbContactsArray(this.atBluetoothActivity);
                    break;
                default:
                    p = null;
                    break;
            }
            int size = p.size();
            while (i < size) {
                Contact neighbour = (Contact) p.get(i);
                if (neighbour.number != null && neighbour.number.contains(searchStr)) {
                    arrayList.add(neighbour);
                } else if (neighbour.name != null && (neighbour.name.contains(searchStr) || neighbour.name.toUpperCase().contains(searchStr))) {
                    arrayList.add(neighbour);
                } else if (neighbour.nameConverted != null && neighbour.nameConverted.contains(searchStr)) {
                    arrayList.add(neighbour);
                }
                i++;
            }
        }
        return arrayList;
    }

    protected void a(ArrayList arrayList) {
        if (arrayList != null) {
            switch (ATBluetoothActivity.GetActivePb(this.atBluetoothActivity)) {
                case 0:
                    if (ATBluetoothActivity.GetPbSearchResult(this.atBluetoothActivity) != null) {
                        ATBluetoothActivity.GetPbSearchResult(this.atBluetoothActivity).clear();
                    }
                    ATBluetoothActivity.SetPbSearchResult(this.atBluetoothActivity, arrayList);
                    break;
                case 1:
                    if (ATBluetoothActivity.GetSimPbSearchResult(this.atBluetoothActivity) != null) {
                        ATBluetoothActivity.GetSimPbSearchResult(this.atBluetoothActivity).clear();
                    }
                    ATBluetoothActivity.SetSimPbSearchResult(this.atBluetoothActivity, arrayList);
                    break;
            }

            ATBluetoothActivity.GetTrListAdapter(this.atBluetoothActivity).notifyDataSetChanged();
        }
    }

    protected Object doInBackground(Object[] objArr) {
        return a((CharSequence[]) objArr);
    }

    protected void onPostExecute(Object obj) {
        a((ArrayList) obj);
    }
}
