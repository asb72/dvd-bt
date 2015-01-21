package com.tw.bt;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import java.io.IOException;

public class ATBluetoothService extends Service {
    private Handler mHandler;
    public twUtil twUtil;
    private int o;
    private MediaPlayer mediaPlayer;
    private int callingState;
    public String incomingContactNumber;
    public String incomingContactName;
    private int bluetoothConnectionState;
    private CallingLayout callingLayout;
    private IncomingCallLayout incomingCallLayout;

    public ATBluetoothService() {
        this.twUtil = null;
        this.o = -1;
        this.mHandler = new BluetoothServiceHandler(this);
        this.bluetoothConnectionState = -1;
    }

    public int PlayFile(String str) {
        this.mediaPlayer.reset();
        try {
            this.mediaPlayer.setDataSource(str);
            this.mediaPlayer.setAudioStreamType(0);
            this.mediaPlayer.prepare();
            return 0;
        } catch (IllegalArgumentException e) {
            return -1;
        } catch (IllegalStateException e2) {
            return -2;
        } catch (IOException e3) {
            return -3;
        } catch (Exception e4) {
            return -4;
        }
    }

    public void SetBluetoothConnectionState(int i) {
        //TODO
        return;
        /*Intent intent = new Intent("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
        intent.putExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", i);
        sendBroadcast(intent);*/
    }

    public void SetBluetoothState(int i) {
        //TODO
        return;
       /*
        Intent intent = new Intent("android.bluetooth.adapter.action.STATE_CHANGED");
        intent.putExtra("android.bluetooth.adapter.extra.STATE", i);
        sendBroadcast(intent);*/
    }

    public void SetBluetothConnectionState(int i) {
        SetBluetoothState(BluetoothAdapter.STATE_ON);
        SetBluetoothConnectionState(i);
    }

    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }

    public void start() {
        this.mediaPlayer.start();
    }

    public void stop() {
        this.mediaPlayer.stop();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mediaPlayer = new MediaPlayer();
        this.incomingCallLayout = new IncomingCallLayout(this);
        this.callingLayout = new CallingLayout(this);
        this.twUtil = twUtil.e();
        if (this.twUtil != null) {
            this.twUtil.addHandler("ATBluetoothService", this.mHandler);
            this.twUtil.write(8);
            this.twUtil.write(3, 3);
        }
    }

    public void onDestroy() {
        this.mediaPlayer.release();
        this.mediaPlayer = null;
        this.incomingCallLayout.hide();
        this.incomingCallLayout = null;
        this.callingLayout.hide();
        this.callingLayout = null;
        if (this.twUtil != null) {
            this.twUtil.removeHandler("ATBluetoothService");
            this.twUtil.close();
            this.twUtil = null;
        }
        super.onDestroy();
    }

    static public int GetBluetoothConnectionState(ATBluetoothService v){
        return v.bluetoothConnectionState;
    }

    static public int b(ATBluetoothService v){
        return v.o;
    }

    static public int PlayFile(ATBluetoothService v, String s){
        return v.PlayFile(s);
    }

    static public void SetIncomingContactNumber(ATBluetoothService v, String s){
        v.incomingContactNumber = s;
    }

    static public void SetIncomingContactName(ATBluetoothService v, String s){
        v.incomingContactName = s;
    }

    static public int GetCallingState(ATBluetoothService v){
        return v.callingState;
    }

    static public twUtil GetTwUtil(ATBluetoothService v){
        return v.twUtil;
    }

    static public void SetBluetoothConnectionState(ATBluetoothService v, int i){
        v.bluetoothConnectionState = i;
    }

    static public void a(ATBluetoothService v, int i){
        v.o = i;
    }

    static public void SetCallingState(ATBluetoothService v, int i){
        v.callingState = i;
    }

    static public Handler GetHandler(ATBluetoothService v){
        return v.mHandler;
    }

    static public void StartPlayer(ATBluetoothService v){
        v.start();
    }

    static public void StopPlayer(ATBluetoothService v){
        v.stop();
    }

    static public void SetBluetoothConnectionState_(ATBluetoothService v, int i){
        v.SetBluetothConnectionState(i);
    }

    static public IncomingCallLayout GetIncomingCallLayout(ATBluetoothService v){
        return v.incomingCallLayout;
    }

    static public CallingLayout GetCallingLayout(ATBluetoothService v){
        return v.callingLayout;
    }

    static public boolean GetPlayingStatus(ATBluetoothService v){
        return v.isPlaying();
    }
}
