package com.example.ab0034.servicefieldtrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    EditText EtxUserName, EtxUserPassword;
    Button BtnLogIn;
    TextView TxtForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EtxUserName = (EditText) findViewById(R.id.EtxUserName);
        EtxUserPassword = (EditText) findViewById(R.id.EtxUserPassword);
        BtnLogIn = (Button) findViewById(R.id.BtnLogIn);
        TxtForgotPassword = (TextView) findViewById(R.id.TxtForgotPassword);
    }
}
