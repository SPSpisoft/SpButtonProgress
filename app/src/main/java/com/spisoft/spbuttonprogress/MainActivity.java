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

        final SProgressButton vv = findViewById(R.id.user_sign_in_button);
        vv.setAnimateDuration(2000);
        vv.setTextPending("wait");
        vv.setText("start");
        vv.setNormal("ssssss","oja h",null,0);
        vv.setFail(null,"back nn",null,0);
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vv.getCurrentMode() <= 0) {
                    vv.setProgress(1);
                }else {
                    vv.setProgress(-1);
                }
            }
        });
    }
}
