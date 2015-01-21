package com.tw.bt;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.os.Message;
import android.tw.john.TWUtil.TWObject;
import android.util.Log;

class BluetoothServiceHandler extends Handler {
    final ATBluetoothService aTBluetoothService;

    BluetoothServiceHandler(ATBluetoothService aTBluetoothService) {
        this.aTBluetoothService = aTBluetoothService;
    }

    public void handleMessage(Message message) {
        int i = 1;
        int bluetoothState = 0;
        try {
            switch (message.what) {
                case 7:
                    if (ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService) != message.arg1) {
                        ATBluetoothService.SetBluetoothConnectionState(this.aTBluetoothService, message.arg1);
                        if (ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService) != BluetoothAdapter.STATE_CONNECTED) {
                            ATBluetoothService.SetCallingState(this.aTBluetoothService, 0);
                            // CallingLayoutManipulations
                            ATBluetoothService.GetHandler(this.aTBluetoothService).sendEmptyMessage(65280);
                        }
                        ATBluetoothService.SetBluetoothConnectionState_(this.aTBluetoothService, ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService));
                    }
                    break;
                case 9:
                    switch (message.arg1) {
                        case 2:
                            bluetoothState = BluetoothAdapter.STATE_CONNECTING;
                            break;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            bluetoothState = BluetoothAdapter.STATE_CONNECTED;
                            break;
                    }
                    if (ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService) != bluetoothState) {
                        ATBluetoothService.SetBluetoothConnectionState(this.aTBluetoothService, bluetoothState);
                        if (ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService) != BluetoothAdapter.STATE_CONNECTED) {
                            ATBluetoothService.SetCallingState(this.aTBluetoothService, 0);
                            // CallingLayoutManipulations
                            ATBluetoothService.GetHandler(this.aTBluetoothService).sendEmptyMessage(65280);
                        }
                        ATBluetoothService.SetBluetoothConnectionState_(this.aTBluetoothService, ATBluetoothService.GetBluetoothConnectionState(this.aTBluetoothService));
                    }
                    break;
                case 11:
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
                    if (ATBluetoothService.GetCallingState(this.aTBluetoothService) != message.arg1) {
                        ATBluetoothService.SetCallingState(this.aTBluetoothService, message.arg1);
                        switch (ATBluetoothService.GetCallingState(this.aTBluetoothService)) {
                            case 0:
                                ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(1283, 0);
                                break;
                            case 1:
                                ATBluetoothService.SetIncomingContactNumber(this.aTBluetoothService, str);
                                ATBluetoothService.SetIncomingContactName(this.aTBluetoothService, str2);
                                ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(1283, 1);
                                break;
                            case 2:
                                ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(1283, 3);
                                break;
                            case 3:
                                ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(1283, 2);
                                break;
                        }
                        // CallingLayoutManipulations
                        ATBluetoothService.GetHandler(this.aTBluetoothService).sendEmptyMessage(65280);
                    }
                    break;
                case 13: // Release sound stream
                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(770, message.arg1);
                    break;
                case 47: // Incoming Call
                    if (message.arg1 == 0) {
                        ATBluetoothService.StopPlayer(this.aTBluetoothService);
                        return;
                    }
                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(770, 2);
                    ATBluetoothService.PlayFile(this.aTBluetoothService, "/system/etc/goc/ring.mp3");
                    ATBluetoothService.StartPlayer(this.aTBluetoothService);
                    break;
                case 513: // Remote control: ACCEPT = 24, REJECT = 25
                    if (message.arg2 == 24 || message.arg2 == 25) {
                        switch (ATBluetoothService.GetCallingState(this.aTBluetoothService)) {
                            case 1:
                                if (message.arg2 == 24 && message.arg1 == 1) {
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(10, 1);
                                } else {
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(10, 2);
                                }
                                break;
                            case 2:
                            case 3:
                            case 4: // outgoing call
                                if (message.arg2 != 24 || message.arg1 == 1) {
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(10, 0);
                                } else {
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(33281, 1, 47);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 769:
                    if (ATBluetoothService.b(this.aTBluetoothService) != message.arg1) {
                        ATBluetoothService.a(this.aTBluetoothService, message.arg1);
                        twUtil a = ATBluetoothService.GetTwUtil(this.aTBluetoothService);
                        if (ATBluetoothService.b(this.aTBluetoothService) != 8) {
                            i = 0;
                        }
                        a.write(46, i);
                    }
                    break;
                case 40456:
                    switch (message.arg1) {
                        case 0:
                            switch (message.arg2) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).a(10, message.arg2, (String) message.obj);
                                    break;
                                case 4:
                                    ATBluetoothService.GetTwUtil(this.aTBluetoothService).write(10, 4, ((String) message.obj).charAt(0));
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case 65280:
                    switch (ATBluetoothService.GetCallingState(this.aTBluetoothService)) {
                        case 0:
                            ATBluetoothService.GetIncomingCallLayout(this.aTBluetoothService).hide();
                            ATBluetoothService.GetCallingLayout(this.aTBluetoothService).hide();
                            if (ATBluetoothService.GetPlayingStatus(this.aTBluetoothService)) {
                                ATBluetoothService.StopPlayer(this.aTBluetoothService);
                            }
                            break;
                        case 1:
                            ATBluetoothService.GetIncomingCallLayout(this.aTBluetoothService).show(true);
                            break;
                        case 2:
                            ATBluetoothService.GetIncomingCallLayout(this.aTBluetoothService).hide();
                            ATBluetoothService.GetCallingLayout(this.aTBluetoothService).show();
                            break;
                        case 3:
                        case 4:
                            ATBluetoothService.GetCallingLayout(this.aTBluetoothService).show();
                            break;
                        default:
                            break;
                    }
                    break;
                case 65281:
                    ATBluetoothService.GetCallingLayout(this.aTBluetoothService).hide();
                    break;
                case 65283:
                    if (ATBluetoothService.b(this.aTBluetoothService) == 8) {
                        ATBluetoothService.GetTwUtil(this.aTBluetoothService).b(false);
                    }
                    break;
                default:
                    break;
            }
        } catch (Throwable e) {
            Log.e("ATBluetoothService", Log.getStackTraceString(e));
        }
    }
}
