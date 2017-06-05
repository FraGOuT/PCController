package com.codeforfun.himanshu.mousecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MouseController extends AppCompatActivity {

    private TextView mousePad;
    private Button leftButton;
    private Button rightButton;

    float initX = 0;
    float initY = 0;
    float disX = 0;
    float disY = 0;

    private boolean mouseMoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouse_controller);

        mousePad = (TextView) findViewById(R.id.mouseTouchPad);
        leftButton = (Button) findViewById(R.id.leftButton);
        rightButton = (Button) findViewById(R.id.rightButton);


        mousePad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(UdpConnectionHelper.isConnected ){//&& out!=null){
                    switch (event.getAction()){

                        case MotionEvent.ACTION_DOWN:
                            initX = event.getX();
                            initY = event.getY();
                            mouseMoved = false;
                            //Log.i("TAGG","Mouse move -- 0");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            disX = event.getX() - initX;
                            disY = event.getY() - initY;
                            initX = event.getX();
                            initY = event.getY();
                            if(disX != 0 || disY != 0){
                                UdpConnectionHelper.sendCommand(disX+","+disY);
                            }
                            mouseMoved = true;
                            break;

                        case MotionEvent.ACTION_UP:
                            if(!mouseMoved){
                                UdpConnectionHelper.sendCommand(AppConstants.LEFT_MOUSE_BUTTON_PRESSED);
                            }
                    }
                }
                return true;
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftButtonPressed();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightButtonPressed();
            }
        });
    }

    public void leftButtonPressed(){
        UdpConnectionHelper.sendCommand(AppConstants.LEFT_MOUSE_BUTTON_PRESSED);
    }


    public void rightButtonPressed(){
        UdpConnectionHelper.sendCommand(AppConstants.RIGHT_MOUSE_BUTTON_PRESSED);
    }
}
