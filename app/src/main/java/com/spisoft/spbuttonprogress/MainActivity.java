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
        vv.setTextPending(" برای استارت کلیک کنید و منتظز  بمانید تا تمام شود بعدش هر جا خاستی برو");
        vv.setText("wait");
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vv.getCurrrentMode() <= 0)
                vv.setProgress(1);
            }
        });
    }
}
