package com.codeforfun.himanshu.mousecontroller;

import java.io.PrintWriter;
import java.net.Socket;

public class UdpConnectionHelper {

    public static String SERVER_ADDRESS = "192.168.0.140";
    public static final int SERVER_PORT = 8282;
    public static boolean isConnected;

    public static Socket mSocket;
    public static PrintWriter out;

    public static void sendCommand(final String message){
        //Log.i("TAGG","Server--"+SERVER_ADDRESS+" m="+message);

        new Thread(new Runnable() {
            @Override
            public void run() {
                out.println(message);
            }
        }).start();
    }
}
