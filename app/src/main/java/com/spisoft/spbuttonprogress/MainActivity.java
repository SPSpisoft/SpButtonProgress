package com.spisoft.spbuttonprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        vv.setInfoKeyShowOnStable(true, null);
        vv.setNormal("hba  hhhhhhhhhhhhkhsd hhhhhhjh shhhhhh f hhhhhhhhhhhhhhhh;lmk;","",null,0);
        vv.autoBackOnFail(true,7000, true);
        vv.setFail(null,"kihas khgo giygliygl gliyg hglgliygiy ggghghg 8ygyiy g",null,0);
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
        vv.setOnInfoClickListener(new SProgressButton.OnInfoClickListener() {
            @Override
            public void onEvent() {
                Toast.makeText(MainActivity.this, "CCCCCCCCc",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
