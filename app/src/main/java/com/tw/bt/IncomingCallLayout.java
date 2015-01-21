package com.tw.bt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

class IncomingCallLayout {
    private LayoutParams layoutParams;
    private TextView incomingContact;
    private boolean visible;
    final ATBluetoothService atBluetoothService;
    private View mView;
    private WindowManager mWindowManager;

    public IncomingCallLayout(ATBluetoothService aTBluetoothService) {
        this.atBluetoothService = aTBluetoothService;
        this.visible = false;
        this.mView = ((LayoutInflater) aTBluetoothService.getSystemService("layout_inflater")).inflate(R.layout.incoming, null);
        this.incomingContact = (TextView) this.mView.findViewById(R.id.incoming);
        ((ImageView) this.mView.findViewById(R.id.answering)).setOnClickListener(new IncomingCallAnswerBtnClick(this, aTBluetoothService));
        ((ImageView) this.mView.findViewById(R.id.reject)).setOnClickListener(new IncomingCallRejectBtnClick(this, aTBluetoothService));
        this.mWindowManager = (WindowManager) aTBluetoothService.getSystemService("window");
        this.layoutParams = new LayoutParams(502, 120, 0, 0, 2002, 40, 1);
    }

    public void hide() {
        if (this.visible) {
            this.mWindowManager.removeView(this.mView);
            this.visible = false;
        }
    }

    public void show(boolean z) {
        if (!this.visible) {
            this.visible = true;
            if (z) {
                this.incomingContact.setVisibility(View.VISIBLE);
                if (this.atBluetoothService.incomingContactName == null || this.atBluetoothService.incomingContactName.length() == 0) {
                    this.incomingContact.setText(this.atBluetoothService.incomingContactNumber);
                } else {
                    this.incomingContact.setText(this.atBluetoothService.incomingContactName);
                }
            } else {
                this.incomingContact.setVisibility(View.INVISIBLE);
            }
            this.mWindowManager.addView(this.mView, this.layoutParams);
        }
    }
}
