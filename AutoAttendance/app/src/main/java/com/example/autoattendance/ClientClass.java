package com.example.autoattendance;

import android.Manifest;
import android.bluetooth.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.*;
import android.widget.*;

import androidx.core.app.ActivityCompat;

import java.io.*;

public class ClientClass extends BaseBluetoothThread {
    public SendReceive sendReceive;

    public ClientClass(BluetoothDevice device1, BluetoothAdapter adapter, Context context, TextView status, Handler handler) {
        super(adapter, context, status, handler);
        device = device1;
        try {
            socket = device.createRfcommSocketToServiceRecord(MY_UUID);
            Toast.makeText(context, "Client Socket Created", Toast.LENGTH_SHORT).show();
            sendReceive = new SendReceive(socket, bluetoothAdapter, context, status, handler);
            if(sendReceive!=null)
                Toast.makeText(context, "SendReceive is not null", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("server socket creation failed SendReceive", e);
        }
    }

    public void run() {
        try {
            socket.connect();
            Message message=Message.obtain();
            message.what=STATE_CONNECTED;
            handler.sendMessage(message);

            sendReceive = new SendReceive(socket,bluetoothAdapter, context, status, handler);
            sendReceive.start();

        } catch (IOException e) {
            e.printStackTrace();
            Message message=Message.obtain();
            message.what=STATE_CONNECTION_FAILED;
            handler.sendMessage(message);
        }
    }
}
