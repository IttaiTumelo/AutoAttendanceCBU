package com.example.autoattendance;

import android.bluetooth.*;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.io.*;

public class SendReceive extends BaseBluetoothThread{

    private final BluetoothSocket bluetoothSocket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SendReceive (BluetoothSocket socket,BluetoothAdapter adapter, Context context, TextView status, Handler handler)
    {
        super(adapter, context, status, handler);
        bluetoothSocket=socket;
        InputStream tempIn=null;
        OutputStream tempOut=null;

        try {
            tempIn=bluetoothSocket.getInputStream();
            tempOut=bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        inputStream=tempIn;
        outputStream=tempOut;
    }

    public void run()
    {
        byte[] buffer=new byte[1024];
        int bytes;

        while (true)
        {
            try {
                bytes=inputStream.read(buffer);
                handler.obtainMessage(STATE_MESSAGE_RECEIVED,bytes,-1,buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] bytes)
    {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
