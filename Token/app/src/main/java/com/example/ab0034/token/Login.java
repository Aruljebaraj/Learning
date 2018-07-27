package com.example.ab0034.token;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ab0034.token.Background.UrlAsync;

import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Login extends AppCompatActivity implements UrlAsync.AsyncResponse {
    ImageView proc;
    EditText EdtName, EdtMobileNumber;
    ProgressDialog progressDialog;
    private boolean PressedOnce;
    String number = "", Otp = "";
    Global g = Global.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        proc = (ImageView) findViewById(R.id.proc);
        EdtName = (EditText) findViewById(R.id.EdtName);
        EdtMobileNumber = (EditText) findViewById(R.id.EdtMobileNumber);

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
//        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS")
//                != PackageManager.PERMISSION_GRANTED) {
//            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
//            ActivityCompat.requestPermissions(Login.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
//        }
    }

    @Override
    public void onBackPressed() {

        if (PressedOnce) {
            PressedOnce = false;
//                TxtHeaderName.setText("Dashboard");
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//                super.onBackPressed();
            PressedOnce = true;


        } else {
//                finish();

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("CONFIRM EXIT")
                    .setContentText("Do you want to exit the app?")
                    .setConfirmText("Okay")
                    .setCancelText("Cancel")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_HOME);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish();
//                            System.exit(0);

                            Toast.makeText(Login.this, "This function is not available", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();

        }
    }

    public void verify() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EdtMobileNumber.getText().toString().trim());
        String name = EdtName.getText().toString();
        Otp = RandomNumber();
        number = EdtMobileNumber.getText().toString();
        g.setName(name.replace(" ", "_"));
        g.setContact(number.trim());
        if (name.equals("")) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        if (stringBuilder.toString().length() <= 9) {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        }
        if (stringBuilder.toString().length() == 10 && name.length() > 0) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Loading please wait..");


            String URl = getResources().getString(R.string.Url) + "SendOtp/{Mobile}/{Otp}".replace("{Mobile}", number)
                    .replace("{Otp}", Otp);
            new UrlAsync(Login.this, URl, Login.this, "Check", false, "").execute("");
        } else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

        }

    }

    private String RandomNumber() {

        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        int i = generator.nextInt(1000000) % 1000000;

        java.text.DecimalFormat f = new java.text.DecimalFormat("000000");
        System.out.println(f.format(i));
        return f.format(i).toString().substring(0, 4);
    }

    @Override
    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {
        try {
            if (output.equalsIgnoreCase("true")) {
                Intent i = new Intent(Login.this, Otp.class);
                number = EdtMobileNumber.getText().toString();
                i.putExtra("Number", number);
                i.putExtra("Otp", Otp);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            } else {
                Toast.makeText(this, "An Error Occurred !!!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
