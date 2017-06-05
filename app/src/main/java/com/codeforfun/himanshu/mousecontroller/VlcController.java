package com.codeforfun.himanshu.mousecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
public class VlcController extends AppCompatActivity {

    ImageButton mPlay, mVolMute, mFullScreen;
    Boolean mPause = false,mMute = false,mFullscreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlc_controller);

        mPlay = (ImageButton) findViewById(R.id.play_pause);
        mVolMute = (ImageButton) findViewById(R.id.volumeMute);
        mFullScreen = (ImageButton) findViewById(R.id.fullScreen);


        findViewById(R.id.play_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPause)
                {
                    mPlay.setImageResource(R.drawable.vlc_pause_icon);
                    mPause =false;
                    UdpConnectionHelper.sendCommand(AppConstants.VLC_PLAY);
                }
                else
                {
                    mPlay.setImageResource(R.drawable.vlc_play_icon);
                    mPause =true;
                    UdpConnectionHelper.sendCommand(AppConstants.VLC_PAUSE);
                }
            }
        });

        findViewById(R.id.volumeMute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMute)
                {
                    mVolMute.setImageResource(R.drawable.volume_inc);
                    mMute =false;
                }
                else
                {
                    mVolMute.setImageResource(R.drawable.vlc_volume_mute);
                    mMute =true;
                }
                UdpConnectionHelper.sendCommand(AppConstants.VLC_MUTE);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_NEXT);
            }
        });

        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_PREVIOUS);
            }
        });

        findViewById(R.id.fastForward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_FAST_FORWARD);
            }
        });

        findViewById(R.id.fastBackward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_FAST_BACKWARD);
            }
        });

        findViewById(R.id.vlcVolInc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_INCREASE_VOLUME);
            }
        });

        findViewById(R.id.vlcVolDec).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_DECREASE_VOLUME);
            }
        });

        findViewById(R.id.fullScreen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFullscreen)
                {
                    mFullScreen.setImageResource(R.drawable.vlc_fullscreen);
                    mFullscreen = false;
                }
                else
                {
                    mFullScreen.setImageResource(R.drawable.vlc_fullscreen_exit);
                    mFullscreen = true;
                }
                UdpConnectionHelper.sendCommand(AppConstants.VLC_FULLSCREEN);
            }
        });

        findViewById(R.id.aspectRatio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand(AppConstants.VLC_ASPECT_RATIO);
            }
        });
    }
}
