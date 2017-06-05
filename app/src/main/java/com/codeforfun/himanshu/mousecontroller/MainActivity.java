package com.codeforfun.himanshu.mousecontroller;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    //private boolean mouseMoved;

    //private Socket mSocket;
    //private PrintWriter out;


    /*private DatagramSocket mdatagramSocket;
    private DatagramPacket mdatagramPacket;*/


    private static String OWN_IP_ADDRESS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        OWN_IP_ADDRESS = String.format("%d.%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));

        Log.i("TAGG","IP = "+OWN_IP_ADDRESS);

        mContext = this;

        Log.i("TAGG","START -- APP");



        findViewById(R.id.connectClient).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.SERVER_ADDRESS = ((EditText)findViewById(R.id.serverAddress)).getText().toString();
                for(int i = 0; i< UdpConnectionHelper.SERVER_ADDRESS.length(); i++){
                    if(!Character.isDigit(UdpConnectionHelper.SERVER_ADDRESS.charAt(i)) && UdpConnectionHelper.SERVER_ADDRESS.charAt(i) != '.'){
                        Toast.makeText(mContext,"Not a valid address",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                new ConnectPhone().execute();
            }
        });
        //new ConnectPhone().execute();
    }

    public class ConnectPhone extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            try{
                Log.i("TAGG","Connection Starting ------Server="+UdpConnectionHelper.SERVER_ADDRESS);
                UdpConnectionHelper.mSocket = new Socket(UdpConnectionHelper.SERVER_ADDRESS,UdpConnectionHelper.SERVER_PORT);
                UdpConnectionHelper.mSocket.setSoTimeout(3000);
                Log.i("TAGG","Connection Success");
            } catch (UnknownHostException e) {
                e.printStackTrace();
                Log.i("TAGG","Error while connecting");
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }

            Log.i("TAGG","Connection Result return -------");
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            UdpConnectionHelper.isConnected = result;
            Toast.makeText(mContext,UdpConnectionHelper.isConnected?"Connected to Server!":"Error while connecting",Toast.LENGTH_SHORT).show();

            try{
                if(UdpConnectionHelper.isConnected){
                    UdpConnectionHelper.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(UdpConnectionHelper.mSocket.getOutputStream())),true);
                    Log.i("TAGG","Connection OUT Success");

                    //Go to all function menu.
                    Intent i = new Intent(mContext,AllFunctions.class);
                    startActivity(i);
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAGG","Error while creating OutWriter.",e);
                Toast.makeText(mContext,"Error while connecting out.",Toast.LENGTH_LONG).show();
            }
        }
    }


    /*public class ConnectPhoneUDP extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;
            Log.i("TAGG","Connection Starting ------");
            String message = "connect_to_server "+OWN_IP_ADDRESS;
            try{
                //InetAddress serverAddr = InetAddress.getByName(params[0]);
                UdpConnectionHelper.mdatagramSocket = new DatagramSocket(UdpConnectionHelper.SERVER_PORT);
                Log.i("TAGG","Socket opened");

                //Send a packet to confirm that the server is present on the network.
                UdpConnectionHelper.sendUDP(message);

                Log.i("TAGG","SENT Connection packet - ");

                //Receive conformation.
                byte[] msgBuff = new byte[1024];
                UdpConnectionHelper.mdatagramPacket = new DatagramPacket(msgBuff,msgBuff.length);

                //Wait for only 10 sec
                UdpConnectionHelper.mdatagramSocket.setSoTimeout(10000);
                UdpConnectionHelper.mdatagramSocket.receive(UdpConnectionHelper.mdatagramPacket);

                Log.i("TAGG","mdat.len = "+UdpConnectionHelper.mdatagramPacket.getLength());
                String line = new String(msgBuff,0,UdpConnectionHelper.mdatagramPacket.getLength());
                Log.i("TAGG","Connection set up line = "+line);

                //mdatagramPacket.setLength(msgBuff.length);
                if(line.contains("Success")){
                    //connection is successful
                    result = true;
                }
                Log.i("TAGG","Connection Success");
            } catch (SocketTimeoutException e){
                UdpConnectionHelper.mdatagramSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                UdpConnectionHelper.mdatagramSocket.close();
            }

            Log.i("TAGG","Connection Result return -------");
            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            isConnected = result;
            Toast.makeText(mContext,isConnected?"Connected to Server!":"Error while connecting",Toast.LENGTH_LONG).show();

            if (isConnected) {
                //out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())),true);
                Log.i("TAGG", "Connection OUT Success");
                findViewById(R.id.connector).setVisibility(View.GONE);
                findViewById(R.id.mouseController).setVisibility(View.VISIBLE);
            }
        }
    }*/





}
