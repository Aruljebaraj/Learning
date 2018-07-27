package com.example.ab0034.token;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Success extends AppCompatActivity {
//    ImageView ImgSuccess;
    TextView TxtTokenNumber, TxtTimer, TxtThankyou;
    String Service = "";
    LinearLayout AnimLayout;
    private boolean PressedOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
//        ImgSuccess = (ImageView) findViewById(R.id.ImgSuccess);
        TxtTokenNumber = (TextView) findViewById(R.id.TxtTokenNumber);
        TxtTimer = (TextView) findViewById(R.id.TxtTimer);
//        Glide.with(this)
//                .load(R.drawable.messagesent)
//                .into(ImgSuccess);
        if (getIntent() != null) {
            Service = getIntent().getStringExtra("ServiceId");
        }
        TxtTokenNumber.setText(Service);

        new CountDownTimer(16000, 1000) {

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onTick(long millisUntilFinished) {
                int time = Math.toIntExact(millisUntilFinished / 1000);
                TxtTimer.setText("Note down your token number,\nthis window will close in " + "" + time + "sec");
            }

            public void onFinish() {
                startActivity(new Intent(Success.this, Login.class));
            }

        }.start();
    }

    @Override
    public void onBackPressed() {
        try {
            startActivity(new Intent(this, Login.class));
            finish();
        } catch (Exception e) {
        }
    }
}
