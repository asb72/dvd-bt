package com.tw.bt;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.MotionEvent;
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
import java.util.HashMap;


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
    public TextToSpeech tts = null;
    private static final int TTS_CHECK_CODE = 98549573;
    private Intent recognizer = null;
    private static final int RECOGNIZER_SEARCH_START_CODE = 1;
    public String last_search_str;

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
        this.last_search_str = "";

        //TTS
        Intent checkTtsIntent = new Intent();
        checkTtsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTtsIntent, TTS_CHECK_CODE);

        recognizer = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizer.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizer.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        trEdit.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_voice_normal), null);
        trEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() >= (trEdit.getRight() - trEdit.getCompoundDrawables()[2].getBounds().width() - trEdit.getPaddingRight())) {
                        VoiceSearch();
                        return true;
                    }
                }
                return false;
            }
        });

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

        if(tts != null)
            tts.shutdown();

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

    public boolean Speak(String utteranceId, String msg) {
        if(tts == null)
            return false;

        mToast.setText(msg);
        mToast.show();

        HashMap<String, String> tts_params = new HashMap<String, String>();
        tts_params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, utteranceId);

        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, tts_params);
        return true;
    }

    public void VoiceSearch() {
        if(isConnected())
            Speak("search_start", "Назовите контакт для поиска");
    }

    public boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();

        if(net != null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            Speak("internet", this.getText(R.string.please_enable_internet).toString());
            mToast.setText(R.string.please_enable_internet);
            mToast.show();
            return false;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            int result = tts.setLanguage(getApplicationContext().getResources().getConfiguration().locale);
                            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                tts.shutdown();
                                mToast.setText(R.string.tts_language_init_error);
                                mToast.show();
                            }else{
                                tts.setOnUtteranceProgressListener(new ttsUtteranceListener());
                            }
                        } else {
                            tts = null;
                            mToast.setText(R.string.tts_init_failed);
                            mToast.show();
                        }
                    }
                });
            } else {
                tts = null;
                mToast.setText(R.string.tts_init_failed);
                mToast.show();
            }
        }else if(requestCode == RECOGNIZER_SEARCH_START_CODE) {
            if(resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(matches.size() > 0) {
                    trEdit.setText(matches.get(0));
                    Speak("contacts_search_begin", getString(R.string.tts_search_begin) + matches.get(0));
                } else {
                    Speak("nothing_recognized", "Не распознал");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class ttsUtteranceListener extends UtteranceProgressListener {
        @Override
        public void onDone(String utteranceId) {
            Log.i("VOICECONTROL", utteranceId);
            if( utteranceId.equals("search_start") ) {
                recognizer.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.recognizer_extra_contacts_search));
                startActivityForResult(recognizer, RECOGNIZER_SEARCH_START_CODE);
            } else if( utteranceId.equals("contacts_search_begin") ) {
                // TODO eliminate race condition better
                while(searchContactsInBackgroundTask != null && searchContactsInBackgroundTask.getStatus() != Status.FINISHED)
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                int size = (isSimPhonebookActive == 1) ? simPbSearchResult.size() :  pbSearchResult.size();
                Log.i("VOICECONTROL", "Found " + Integer.toString( size));
/*
                for(int i = 0; i < size; i++) {

                }
                recognizer.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.recognizer_select_contact_for_calling));
                startActivityForResult(recognizer, RECOGNIZER_SEARCH_START_CODE);
                */
            }
        }

        @Override
        public void onError(String utteranceId) {
        }

        @Override
        public void onStart(String utteranceId) {
        }
    }
}
