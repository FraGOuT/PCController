package com.codeforfun.himanshu.mousecontroller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AllFunctions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_functions);

        findViewById(R.id.mouseController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MouseController.class);
                startActivity(i);
            }
        });

        findViewById(R.id.vlcController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),VlcController.class);
                startActivity(i);
            }
        });

        findViewById(R.id.powerpointController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),PowerPointSlideShow.class);
                startActivity(i);
            }
        });
    }
}
