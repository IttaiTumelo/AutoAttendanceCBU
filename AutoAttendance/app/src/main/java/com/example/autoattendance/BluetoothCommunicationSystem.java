package com.example.autoattendance;

import android.bluetooth.*;
import android.widget.*;

import java.util.UUID;

public class BluetoothCommunicationSystem {


    SendReceive sendReceive;

    public static final int STATE_LISTENING = 1;
    public static final int STATE_CONNECTING=2;
    public static final int STATE_CONNECTED=3;
    public static final int STATE_CONNECTION_FAILED=4;
    public static final int STATE_MESSAGE_RECEIVED=5;

    public int REQUEST_ENABLE_BLUETOOTH=6;

    public static final String APP_NAME = "BTChat";
    public static final UUID MY_UUID=UUID.fromString("8ce255c0-223a-11e0-ac64-0803450c9a66");

}