package com.codeforfun.himanshu.mousecontroller;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

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
                ConnectionHelper.SERVER_ADDRESS = ((EditText)findViewById(R.id.serverAddress)).getText().toString();
                for(int i = 0; i< ConnectionHelper.SERVER_ADDRESS.length(); i++){
                    if(!Character.isDigit(ConnectionHelper.SERVER_ADDRESS.charAt(i)) && ConnectionHelper.SERVER_ADDRESS.charAt(i) != '.'){
                        Toast.makeText(mContext,"Not a valid address",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                new ConnectPhone().execute();
            }
        });
    }

    public class ConnectPhone extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;

            try{
                Log.i("TAGG","Connection Starting ------Server="+ ConnectionHelper.SERVER_ADDRESS);
                ConnectionHelper.mSocket = new Socket(ConnectionHelper.SERVER_ADDRESS, ConnectionHelper.SERVER_PORT);
                ConnectionHelper.mSocket.setSoTimeout(3000);
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

            ConnectionHelper.isConnected = result;
            Toast.makeText(mContext, ConnectionHelper.isConnected?"Connected to Server!":"Error while connecting",Toast.LENGTH_SHORT).show();

            try{
                if(ConnectionHelper.isConnected){
                    ConnectionHelper.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(ConnectionHelper.mSocket.getOutputStream())),true);
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
}
