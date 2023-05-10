package com.example.autoattendance;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.UUID;

public class BaseBluetoothThread extends Thread{

    public static final String APP_NAME = "BTChat";
    public static final UUID MY_UUID = UUID.fromString("a4c4f8f0-5a2b-11ec-9d1a-0800200c9a66");
    public final Context context;

    public Button listen, send, listDevices;
    public ListView listView;
    public final TextView status;
    public TextView msg_box;
    public EditText writeMsg;

    public final BluetoothAdapter bluetoothAdapter;
    public BluetoothDevice[] btArray;
    public BluetoothDevice device;
    public BluetoothSocket socket;

    public static final int STATE_LISTENING = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTION_FAILED = 4;
    public static final int STATE_MESSAGE_RECEIVED = 5;
//
//    private final BluetoothSocket bluetoothSocket;
//    private final InputStream inputStream;
//    private final OutputStream outputStream;

    public final Handler handler;


    public BaseBluetoothThread( BluetoothAdapter bluetoothAdapter, Context context, TextView status, Handler handler) {
        this.context = context;
        this.status = status;
        this.bluetoothAdapter = bluetoothAdapter;
        this.handler = handler;
    }
}
