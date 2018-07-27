package com.example.ab0034.servicefieldtrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tracking extends AppCompatActivity implements View.OnClickListener {
    ImageView ImgBack;
    TextView TxtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        try {
            TxtStatus=(TextView)findViewById(R.id.TxtStatus);
            TxtStatus.setBackground(getResources().getDrawable(R.drawable.arrowed_bg));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImgBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(Tracking.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


    class LogDto{
        String LeadLogTitle;
        String LeadLogDateTime;
        String LeadScheduleRejectTime;
    }
}
