package com.spisoft.spbuttonprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.spisoft.spbutton.SProgressButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SProgressButton vv = findViewById(R.id.spb);
        vv.setText("dddddd");
        vv.setTextPending("wait");
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.setProgress(1);
            }
        });
    }
}
