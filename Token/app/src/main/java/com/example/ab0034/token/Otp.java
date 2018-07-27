package com.example.ab0034.token;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ab0034.token.Background.SmsListener;
import com.example.ab0034.token.Background.SmsReceivers;

public class Otp extends AppCompatActivity implements View.OnClickListener {
    ImageView proc;
    EditText EdtOne, Edttwo, Edtthree, Edtfour;
    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt0, txtCLR, txtCE, TxtNumber;
    ProgressDialog progressDialog;
    String Number = "",StoreOtp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        EdtOne = (EditText) findViewById(R.id.EdtOne);
        Edttwo = (EditText) findViewById(R.id.Edttwo);
        Edtthree = (EditText) findViewById(R.id.Edtthree);
        Edtfour = (EditText) findViewById(R.id.Edtfour);
        EdtOne.setShowSoftInputOnFocus(false);
        Edttwo.setShowSoftInputOnFocus(false);
        Edtthree.setShowSoftInputOnFocus(false);
        Edtfour.setShowSoftInputOnFocus(false);

        EdtOne.setInputType(InputType.TYPE_NULL);
        Edttwo.setInputType(InputType.TYPE_NULL);
        Edtthree.setInputType(InputType.TYPE_NULL);
        Edtfour.setInputType(InputType.TYPE_NULL);

        EdtOne.addTextChangedListener(new GenericTextWatcher(EdtOne));
        Edttwo.addTextChangedListener(new GenericTextWatcher(Edttwo));
        Edtthree.addTextChangedListener(new GenericTextWatcher(Edtthree));
        Edtfour.addTextChangedListener(new GenericTextWatcher(Edtfour));
        TxtNumber = (TextView) findViewById(R.id.TxtNumber);

        txt0 = (TextView) findViewById(R.id.txt0);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txtCLR = (TextView) findViewById(R.id.txtCLR);
        txtCE = (TextView) findViewById(R.id.txtCE);
        proc = (ImageView) findViewById(R.id.proc);
        if (getIntent() != null) {
            Number = getIntent().getStringExtra("Number");
            StoreOtp = getIntent().getStringExtra("Otp");
        }
        TxtNumber.setText(TxtNumber.getText() + Number);
        ClickListener();

        SmsReceivers.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {

                String otp = messageText;
                otp = otp.replace("Your Otp is ", "");
                Toast.makeText(Otp.this, otp, Toast.LENGTH_SHORT).show();
                otp = otp.substring(0, 6);
                EdtOne.setText(otp.substring(0, 1));
                Edttwo.setFocusable(true);

                Edttwo.setText(otp.substring(1, 2));
                Edtthree.setFocusable(true);

                Edtthree.setText(otp.substring(2, 3));
                Edtfour.setFocusable(true);

                Edtfour.setText(otp.substring(3, 4));
                verify();
            }
        });

    }

    public void ClickListener() {
        proc.setOnClickListener(this);
        txt0.setOnClickListener(this);
        txt1.setOnClickListener(this);
        txt2.setOnClickListener(this);
        txt3.setOnClickListener(this);
        txt4.setOnClickListener(this);
        txt5.setOnClickListener(this);
        txt6.setOnClickListener(this);
        txt7.setOnClickListener(this);
        txt8.setOnClickListener(this);
        txt9.setOnClickListener(this);
        txtCLR.setOnClickListener(this);
        txtCE.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proc:
                verify();
                break;
            case R.id.txtCLR:
                LoadText(1, "");
                break;
            case R.id.txtCE:
                LoadText(2, "");
                break;
            default:
                LoadText(0, ((TextView) findViewById(v.getId())).getText().toString());
                break;
        }

    }

    private void LoadText(int Type, String Text) {
        String One = EdtOne.getText().toString();
        String Two = Edttwo.getText().toString();
        String Three = Edtthree.getText().toString();
        String Four = Edtfour.getText().toString();
        if (Type == 0) {
            if (One.length() < 1) {
                EdtOne.setText(Text);
            } else if (Two.length() < 1) {
                Edttwo.setText(Text);

            } else if (Three.length() < 1) {
                Edtthree.setText(Text);
            } else if (Four.length() < 1) {
                Edtfour.setText(Text);
            }
        } else if (Type == 1) {
            if (Four.length() == 1) {
                Edtfour.setText(Text);
            } else if (Three.length() == 1) {
                Edtthree.setText(Text);

            } else if (Two.length() == 1) {
                Edttwo.setText(Text);
            } else if (One.length() == 1) {
                EdtOne.setText(Text);
            }
        } else {
            EdtOne.setText("");
            Edttwo.setText("");
            Edtthree.setText("");
            Edtfour.setText("");
        }


    }

    class GenericTextWatcher implements TextWatcher {
        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.EdtOne:
                    if (text.length() == 1)
                        Edttwo.requestFocus();
                    break;
                case R.id.Edttwo:
                    if (text.length() == 1)
                        Edtthree.requestFocus();
                    break;
                case R.id.Edtthree:
                    if (text.length() == 1)
                        Edtfour.requestFocus();
                    break;
                case R.id.Edtfour:

                    break;

            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }

    public void verify() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EdtOne.getText().toString().trim()).append(Edttwo.getText().toString().trim())
                .append(Edtthree.getText().toString().trim()).append(Edtfour.getText().toString().trim());


        if (stringBuilder.toString().length() == 0) {
            Toast.makeText(this, "Enter Otp", Toast.LENGTH_SHORT).show();
        }
        if (stringBuilder.toString().length() == 4) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setMessage("Loading please wait..");
            if (StoreOtp.equals(stringBuilder.toString())) {
                Intent i = new Intent(Otp.this, Services.class);
                i.putExtra("Otp", StoreOtp);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            } else {
                Toast.makeText(this, "Invalid Otp", Toast.LENGTH_SHORT).show();

            }
        }
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
}
