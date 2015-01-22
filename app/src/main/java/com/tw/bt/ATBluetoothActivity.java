package com.tw.bt;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ATBluetoothActivity extends Activity {
    public static final int[] BtnActionNames;
    public static final int[] ag;
    public ListView tList;
    public LinearLayout bottomLayout;
    public LinearLayout a2dbLayout;
    public ListView trList;
    public LinearLayout dialLayout;
    public TextView dightTextView;
    public RelativeLayout centerLayout;
    public EditText trEdit;
    public ProgressBar progressBar;
    public int NotBC6;
    public tListAdapter tListAdapter;
    public trListAdapter trListAdapter;
    public SearchContactsInBackgroundTask searchContactsInBackgroundTask;
    public int activeTab;
    public int currentCallog;
    public int isSimPhonebookActive;
    public int Q;
    public String CurrentPairedBtNeighbour;
    public int S;
    public int lastActiveTab;
    public ArrayList btNeighbours;
    public ArrayList BTNeighbours;
    public ArrayList PbContactsArray;
    public ArrayList pbSearchResult;
    public ArrayList SimPbContactsArray;
    public ArrayList simPbSearchResult;
    public ArrayList callogMissed;
    public ArrayList callogAnswered;
    public ArrayList callogOutgoing;
    public String[] settingsValues;
    public int[] af;
    public Handler mHandler;
    public Toast mToast;
    public twUtil twUtil;
    public int callingState;
    public String telNumberStr;
    public String telContactStr;
    public int t;
    public ImageView tl_0_Img;
    public ImageView tl_1_Img;
    public ImageView tl_2_Img;
    public ImageView tl_3_Img;

    static {
        BtnActionNames = new int[]{R.string.device_name, R.string.pin, R.string.auto_connect, R.string.auto_answer};
        ag = new int[]{0, 0, R.array.auto_array, R.array.auto_array};
    }

    public ATBluetoothActivity() {
        this.twUtil = null;
        this.NotBC6 = 0;
        this.mHandler = new ATBluetoothActivityHandler(this);
        this.searchContactsInBackgroundTask = null;
        this.t = -1;
        this.lastActiveTab = -1;
        this.btNeighbours = new ArrayList();
        this.BTNeighbours = new ArrayList();
        this.PbContactsArray = new ArrayList();
        this.pbSearchResult = null;
        this.SimPbContactsArray = new ArrayList();
        this.simPbSearchResult = null;
        this.callogMissed = new ArrayList();
        this.callogAnswered = new ArrayList();
        this.callogOutgoing = new ArrayList();
        this.settingsValues = new String[4];
        this.af = new int[]{0, 0, -1, -1};
    }

    public void HideTrEdit() {
        ShowKeyboard(false);
        this.trEdit.setText("");
        this.trEdit.setVisibility(View.GONE);
    }

    public void ShowKeyboard(boolean z) {
        if (z) {
            ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.trEdit, 0);
        } else if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 2);
        }
    }

    private void ToggleTrEditVisibility() {
        if (this.trEdit.getVisibility() == View.VISIBLE) {
            HideTrEdit();
            return;
        }
        this.trEdit.setVisibility(View.VISIBLE);
        this.trEdit.requestFocus();
        ShowKeyboard(true);
    }

    public void activateTab(int tab) {
        int i2 = 1;
        if (this.activeTab != tab) {
            if (this.activeTab != 0) {
                ((ImageView) findViewById(this.activeTab)).getBackground().setLevel(0);
            }
            this.activeTab = tab;
            ((ImageView) findViewById(this.activeTab)).getBackground().setLevel(1);
            if (this.activeTab == R.id.dial) {
                // HideCallingLayout
                this.twUtil.sendHandler("ATBluetoothService", 65281);
            } else {
                // CallingLayoutManipulations
                this.twUtil.sendHandler("ATBluetoothService", 65280);
            }
            Drawable background;
            switch (this.activeTab) {
                case R.id.dial:
                    this.tList.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.INVISIBLE);
                    this.tl_0_Img.setImageResource(R.drawable.call);
                    this.tl_0_Img.getBackground().setLevel(0);
                    this.tl_1_Img.setImageResource(R.drawable.hung);
                    this.tl_1_Img.getBackground().setLevel(0);
                    this.tl_2_Img.setImageResource(R.drawable.voice);
                    this.tl_2_Img.getBackground().setLevel(0);
                    this.tl_3_Img.setImageResource(R.drawable.mute);
                    this.tl_3_Img.setVisibility(this.NotBC6 == 1 ? View.GONE : View.VISIBLE);
                    this.centerLayout.setVisibility(View.INVISIBLE);
                    this.dialLayout.setVisibility(View.VISIBLE);
                    this.bottomLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.calllog:
                    this.tList.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.INVISIBLE);
                    this.tl_0_Img.setImageResource(R.drawable.call_missed);
                    this.tl_0_Img.getBackground().setLevel(this.currentCallog == 0 ? 1 : 0);
                    this.tl_1_Img.setImageResource(R.drawable.call_received);
                    this.tl_1_Img.getBackground().setLevel(this.currentCallog == 1 ? 1 : 0);
                    this.tl_2_Img.setImageResource(R.drawable.call_outgoing);
                    background = this.tl_2_Img.getBackground();
                    if (this.currentCallog != 2) {
                        i2 = 0;
                    }
                    background.setLevel(i2);
                    this.tl_3_Img.setImageResource(R.drawable.remove);
                    this.tl_3_Img.setVisibility(View.VISIBLE);
                    this.dialLayout.setVisibility(View.INVISIBLE);
                    this.centerLayout.setVisibility(View.VISIBLE);
                    this.bottomLayout.setVisibility(View.VISIBLE);
                    this.trListAdapter.notifyDataSetChanged();
                    break;
                case R.id.phonebook:
                    this.tList.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.INVISIBLE);
                    this.tl_0_Img.setImageResource(R.drawable.pb_phone);
                    this.tl_0_Img.getBackground().setLevel(this.isSimPhonebookActive == 0 ? 1 : 0);
                    this.tl_1_Img.setImageResource(R.drawable.pb_sim);
                    background = this.tl_1_Img.getBackground();
                    if (this.isSimPhonebookActive != 1) {
                        i2 = 0;
                    }
                    background.setLevel(i2);
                    this.tl_2_Img.setImageResource(R.drawable.download);
                    this.tl_2_Img.getBackground().setLevel(0);
                    this.tl_3_Img.setImageResource(R.drawable.remove);
                    this.tl_3_Img.setVisibility(View.VISIBLE);
                    this.dialLayout.setVisibility(View.INVISIBLE);
                    this.centerLayout.setVisibility(View.VISIBLE);
                    this.bottomLayout.setVisibility(View.VISIBLE);
                    this.trListAdapter.notifyDataSetChanged();
                    break;
                case R.id.a2dp:
                    this.tList.setVisibility(View.INVISIBLE);
                    this.bottomLayout.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.VISIBLE);
                    this.twUtil.b(true);
                    break;
                case R.id.pair:
                    this.tList.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.INVISIBLE);
                    this.tl_0_Img.setImageResource(R.drawable.paired);
                    this.tl_0_Img.getBackground().setLevel(this.Q == 0 ? 1 : 0);
                    this.tl_1_Img.setImageResource(R.drawable.search);
                    background = this.tl_1_Img.getBackground();
                    if (this.Q != 1) {
                        i2 = 0;
                    }
                    background.setLevel(i2);
                    this.tl_2_Img.setImageResource(R.drawable.disconnect);
                    this.tl_2_Img.getBackground().setLevel(0);
                    this.tl_3_Img.setImageResource(R.drawable.remove);
                    this.tl_3_Img.setVisibility(View.VISIBLE);
                    this.dialLayout.setVisibility(View.INVISIBLE);
                    if (this.t != 2) {
                        this.progressBar.setVisibility(View.INVISIBLE);
                    }
                    this.centerLayout.setVisibility(View.VISIBLE);
                    this.bottomLayout.setVisibility(View.VISIBLE);
                    this.trListAdapter.notifyDataSetChanged();
                    break;
                case R.id.setting:
                    this.bottomLayout.setVisibility(View.INVISIBLE);
                    this.a2dbLayout.setVisibility(View.INVISIBLE);
                    this.tList.setVisibility(View.VISIBLE);
                    this.twUtil.write(30);
                    this.twUtil.write(32);
                    this.twUtil.write(34);
                    break;
            }
            HideTrEdit();
        }
    }

    public void onBClick(View view) {
        if (this.t != 2) {
            switch (view.getId()) {
                case R.id.dial:
                case R.id.calllog:
                case R.id.phonebook:
                case R.id.a2dp:
                    return;
            }
        }
        activateTab(view.getId());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prev:
                this.twUtil.write(20, 3);
                break;
            case R.id.pp:
                this.twUtil.write(20, 0);
                break;
            case R.id.next:
                this.twUtil.write(20, 2);
                break;
            case R.id.delete:
                String toString = this.dightTextView.getText().toString();
                if (toString.length() > 0) {
                    this.dightTextView.setText(toString.substring(0, toString.length() - 1));
                }
                break;
            case R.id.home:
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setFlags(268435456);
                intent.addCategory("android.intent.category.HOME");
                startActivity(intent);
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        this.mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        setContentView(R.layout.atbluetooth);
        this.bottomLayout = (LinearLayout) findViewById(R.id.t_normal);
        this.tl_0_Img = (ImageView) findViewById(R.id.tl_0);
        this.tl_1_Img = (ImageView) findViewById(R.id.tl_1);
        this.tl_2_Img = (ImageView) findViewById(R.id.tl_2);
        this.tl_3_Img = (ImageView) findViewById(R.id.tl_3);
        this.a2dbLayout = (LinearLayout) findViewById(R.id.tr_a2dp);
        this.centerLayout = (RelativeLayout) findViewById(R.id.tr_list_bg);
        this.progressBar = (ProgressBar) findViewById(R.id.tr_progress);
        this.dialLayout = (LinearLayout) findViewById(R.id.tr_dial);
        this.dightTextView = (TextView) findViewById(R.id.digit);
        this.trEdit = (EditText) findViewById(R.id.tr_edit);
        this.trEdit.addTextChangedListener(new EditTextWatcher(this));
        ((TextView) findViewById(R.id.digit_0)).setOnLongClickListener(new ZeroDightBtnClick(this));
        ((ImageView) findViewById(R.id.delete)).setOnLongClickListener(new BackSpaceBtnClick(this));
        this.trList = (ListView) findViewById(R.id.tr_list);
        this.trList.setOnItemClickListener(new trListViewItemClick(this));
        this.trList.setOnItemLongClickListener(new ChangeBTNeigbourTypeLongClick(this));
        this.trListAdapter = new trListAdapter(this, this);
        this.trList.setAdapter(this.trListAdapter);
        this.tList = (ListView) findViewById(R.id.t_list);
        this.tList.setOnItemClickListener(new tListViewItemClick(this));
        this.tListAdapter = new tListAdapter(this, this);
        this.tList.setAdapter(this.tListAdapter);
        this.twUtil = twUtil.e();
        this.NotBC6 = this.twUtil.write(63488);
        activateTab(R.id.dial);
    }

    public void onDClick(View view) {
        if (this.callingState == 0) {
            this.dightTextView.setText("" + this.dightTextView.getText() + ((TextView) view).getText().charAt(0));
        } else {
            this.twUtil.write(10, 4, ((TextView) view).getText().charAt(0));
        }
    }

    protected void onDestroy() {
        this.twUtil.sendHandler("ATBluetoothService", 65283);
        if (!(this.searchContactsInBackgroundTask == null || this.searchContactsInBackgroundTask.getStatus() == Status.FINISHED)) {
            this.searchContactsInBackgroundTask.cancel(true);
            this.searchContactsInBackgroundTask = null;
        }
        this.twUtil.removeHandler("ATBluetoothActivity");
        this.twUtil.close();
        this.twUtil = null;
        super.onDestroy();
    }

    protected void onPause() {
        this.progressBar.setVisibility(View.INVISIBLE);
        this.twUtil.write(28, 0);
        // CallingLayoutManipulations
        this.twUtil.sendHandler("ATBluetoothService", 65280);
        if (this.S == 1) {
            this.twUtil.write(22, 2);
            this.S = 0;
        }
        this.twUtil.removeHandler("ATBluetoothActivity");
        this.twUtil.f(136);
        super.onPause();
    }

    protected void onResume() {

        this.twUtil.f(8);
        this.twUtil.addHandler("ATBluetoothActivity", this.mHandler);
        this.twUtil.write(8);
        this.twUtil.write(10, 255);

        if (this.t == 2 && this.CurrentPairedBtNeighbour != null) {
            this.twUtil.write(26, 255);
        }
        if (this.activeTab == R.id.a2dp) {
            this.twUtil.b(true);
        } else if (this.activeTab == R.id.dial) {
            // HideCallingLayout
            this.twUtil.sendHandler("ATBluetoothService", 65281);
        }
        super.onResume();
    }

    public void onTlClick(View view) {
        Dialog dialog;
        LayoutParams attributes;
        switch (view.getId()) {
            case R.id.tl_0:
                switch (this.activeTab) {
                    case R.id.dial:
                        if (this.callingState == 0) {
                            this.telNumberStr = this.dightTextView.getText().toString();
                            if (this.telNumberStr != null && this.telNumberStr.length() != 0) {
                                this.telContactStr = null;
                                this.twUtil.a(10, 3, this.telNumberStr);
                            }
                        } else if (this.callingState == 1) {
                            this.twUtil.write(10, 1);
                        }
                        break;
                    case R.id.calllog:
                        this.currentCallog = 0;
                        this.tl_0_Img.getBackground().setLevel(1);
                        this.tl_1_Img.getBackground().setLevel(0);
                        this.tl_2_Img.getBackground().setLevel(0);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.phonebook:
                        if (this.S == 1 || this.isSimPhonebookActive != 0) {
                            HideTrEdit();
                        } else {
                            ToggleTrEditVisibility();
                        }
                        this.isSimPhonebookActive = 0;
                        this.tl_0_Img.getBackground().setLevel(1);
                        this.tl_1_Img.getBackground().setLevel(0);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.pair:
                        this.Q = 0;
                        this.tl_0_Img.getBackground().setLevel(1);
                        this.tl_1_Img.getBackground().setLevel(0);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.tl_1:
                switch (this.activeTab) {
                    case R.id.dial:
                        if (this.callingState == 1) {
                            this.twUtil.write(10, 2);
                        } else {
                            this.twUtil.write(10, 0);
                        }
                        break;
                    case R.id.calllog:
                        this.currentCallog = 1;
                        this.tl_0_Img.getBackground().setLevel(0);
                        this.tl_1_Img.getBackground().setLevel(1);
                        this.tl_2_Img.getBackground().setLevel(0);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.phonebook:
                        if (this.S == 1 || this.isSimPhonebookActive != 1) {
                            HideTrEdit();
                        } else {
                            ToggleTrEditVisibility();
                        }
                        this.isSimPhonebookActive = 1;
                        this.tl_0_Img.getBackground().setLevel(0);
                        this.tl_1_Img.getBackground().setLevel(1);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.pair:
                        this.Q = 1;
                        this.tl_0_Img.getBackground().setLevel(0);
                        this.tl_1_Img.getBackground().setLevel(1);
                        this.progressBar.setVisibility(View.VISIBLE);
                        this.btNeighbours.clear();
                        this.twUtil.write(28, 255);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.tl_2:
                switch (this.activeTab) {
                    case R.id.dial:
                        this.twUtil.write(12);
                        break;
                    case R.id.calllog:
                        this.currentCallog = 2;
                        this.tl_0_Img.getBackground().setLevel(0);
                        this.tl_1_Img.getBackground().setLevel(0);
                        this.tl_2_Img.getBackground().setLevel(1);
                        this.trListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.phonebook:
                        if (this.S == 1) {
                            this.twUtil.write(22, 2);
                            return;
                        }
                        switch (this.isSimPhonebookActive) {
                            case 0:
                                dialog = new Builder(this)
                                        .setIcon(R.drawable.pb_phone)
                                        .setTitle(R.string.download_pb_phone)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DownloadPhonePbBtnClick(this))
                                        .setNegativeButton(R.string.alert_dialog_cancel, null)
                                        .show();
                                attributes = dialog.getWindow().getAttributes();
                                attributes.dimAmount = 0.0f;
                                dialog.getWindow().setAttributes(attributes);
                                break;
                            case 1:
                                dialog = new Builder(this)
                                        .setIcon(R.drawable.pb_sim)
                                        .setTitle(R.string.download_pb_sim)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DownloadSimPbBtnClick(this))
                                        .setNegativeButton(R.string.alert_dialog_cancel, null)
                                        .show();
                                attributes = dialog.getWindow().getAttributes();
                                attributes.dimAmount = 0.0f;
                                dialog.getWindow().setAttributes(attributes);
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.pair:
                        this.twUtil.write(6, 0);
                        this.twUtil.write(38, 0, 0);
                        break;
                    default:
                        break;
                }
                break;
            case R.id.tl_3:
                switch (this.activeTab) {
                    case R.id.dial:
                        this.twUtil.write(25);
                        break;
                    case R.id.calllog:
                        dialog = new Builder(this)
                                .setIcon(R.drawable.calllog)
                                .setTitle(R.string.remove_calllog)
                                .setPositiveButton(R.string.alert_dialog_ok, new CallLogRemoveCalLogBtnClick(this))
                                .setNegativeButton(R.string.alert_dialog_cancel, null)
                                .show();
                        attributes = dialog.getWindow().getAttributes();
                        attributes.dimAmount = 0.0f;
                        dialog.getWindow().setAttributes(attributes);
                        break;
                    case R.id.phonebook:
                        switch (this.isSimPhonebookActive) {
                            case 0:
                                dialog = new Builder(this).setIcon(R.drawable.pb_phone)
                                        .setTitle(R.string.remove_pb_phone)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DeletePhonePbBtnClick(this))
                                        .setNegativeButton(R.string.alert_dialog_cancel, null)
                                        .show();
                                attributes = dialog.getWindow().getAttributes();
                                attributes.dimAmount = 0.0f;
                                dialog.getWindow().setAttributes(attributes);
                                break;
                            case 1:
                                dialog = new Builder(this).setIcon(R.drawable.pb_sim)
                                        .setTitle(R.string.remove_pb_sim)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DeleteSimPbBtnClick(this))
                                        .setNegativeButton(R.string.alert_dialog_cancel, null)
                                        .show();
                                attributes = dialog.getWindow().getAttributes();
                                attributes.dimAmount = 0.0f;
                                dialog.getWindow().setAttributes(attributes);
                                break;
                            default:
                                break;
                        }
                        break;
                    case R.id.pair:
                        switch (this.Q) {
                            case 0:
                                dialog = new Builder(this)
                                        .setIcon(R.drawable.paired)
                                        .setTitle(R.string.remove_pair)
                                        .setPositiveButton(R.string.alert_dialog_ok, new DeleteAllPairRecordsBtnClick(this))
                                        .setNegativeButton(R.string.alert_dialog_cancel, null)
                                        .show();
                                attributes = dialog.getWindow().getAttributes();
                                attributes.dimAmount = 0.0f;
                                dialog.getWindow().setAttributes(attributes);
                                break;
                            case 1:
                                this.btNeighbours.clear();
                                this.trListAdapter.notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    public static int GetActivePb(ATBluetoothActivity a){
        return a.isSimPhonebookActive;
    }

    static public ArrayList GetPbSearchResult(ATBluetoothActivity v){
        return v.pbSearchResult;
    }

    static public ArrayList SetPbSearchResult(ATBluetoothActivity v, ArrayList l){
        return v.pbSearchResult = l;
    }

    static public ArrayList GetSimPbSearchResult(ATBluetoothActivity v){
        return v.simPbSearchResult;
    }

    static public trListAdapter GetTrListAdapter(ATBluetoothActivity v){
        return v.trListAdapter;
    }

    static public ArrayList SetSimPbSearchResult(ATBluetoothActivity v, ArrayList l){
        return v.simPbSearchResult = l;
    }

    static public ArrayList GetPbContactsArray(ATBluetoothActivity v){
        return v.PbContactsArray;
    }

    static public ArrayList GetSimPbContactsArray(ATBluetoothActivity v){
        return v.SimPbContactsArray;
    }

    static Comparator<Contact> comparator = new Comparator<Contact>() {
        public int compare(Contact u1, Contact u2) {
            int res;
            int l1 = u1.getName().length();
            int l2 = u2.getName().length();

            if( l1 == 0 && l2 == 0 )
                res = u1.getNumber().compareTo(u2.getNumber());
            else
                res = u1.getName().compareTo(u2.getName());

            if( l2 == 0 )
                res = -1;
			/*
			else {
				char ch = u2.getName().charAt(0);
				if( ch == '+' || Character.isDigit(ch) )
					res = -1;
			}*/

            return res;
        }
    };

    public void AddContact(ArrayList<Contact> list, Contact contact) {
        int index = Collections.binarySearch(list, contact, comparator);
        list.add((index < 0) ? -index - 1 : index, contact);
    }
}
