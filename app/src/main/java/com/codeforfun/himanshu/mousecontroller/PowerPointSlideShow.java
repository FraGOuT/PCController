package com.codeforfun.himanshu.mousecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PowerPointSlideShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_point_slide_show);


        findViewById(R.id.previousSlide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand("pwr_prev_slide");
            }
        });

        findViewById(R.id.nextSlide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UdpConnectionHelper.sendCommand("pwr_next_slide");
            }
        });
    }
}
