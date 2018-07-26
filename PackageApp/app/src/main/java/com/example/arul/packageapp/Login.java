package com.example.arul.packageapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arul.packageapp.Background.BlurBuilder;
import com.example.arul.packageapp.Background.UrlAsync;
import com.example.arul.packageapp.Model.EmployeeModel;
import com.loopj.android.http.HttpGet;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class Login extends AppCompatActivity implements View.OnClickListener, UrlAsync.AsyncResponse {
    TextView BtnSignIn;
    TextView TxtRegister;
    EditText EtxUserName, EtxPassword;
    String username, password;
    ProgressDialog pDialog;
    SQLiteDatabase mydatabase;
    String DB_NAME = "Dailyfresh";
    LinearLayout Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initialize();
        clickListeners();
    }

    private void clickListeners() {
        BtnSignIn.setOnClickListener(this);
        TxtRegister.setOnClickListener(this);
    }

    private void Initialize() {
        EtxUserName = (EditText) findViewById(R.id.Edtusername);
        EtxPassword = (EditText) findViewById(R.id.Edtpassword);
        BtnSignIn = (TextView) findViewById(R.id.BtnSignIn);
        TxtRegister = (TextView) findViewById(R.id.TxtRegister);
        Main = (LinearLayout) findViewById(R.id.Main);
        Bitmap resultBmp = BlurBuilder.blur(this, BitmapFactory.decodeResource(getResources(), R.drawable.daily_fresh_bg_one));
        BitmapDrawable background = new BitmapDrawable(resultBmp);
        Main.setBackgroundDrawable(background);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BtnSignIn:
                username = EtxUserName.getText().toString().trim();
                password = EtxPassword.getText().toString().trim();
                StringBuilder Url = new StringBuilder();
                Url.append(getResources().getString(R.string.UrlPrefix)).append("Token?")
                        .append("Mobile=").append(username).append("&Password=").append(password);

                new UrlAsync(Login.this, Url.toString(), Login.this, "Login", false, "").execute("");
                pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Logging-in please wait...");
//                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
                break;
            case R.id.TxtRegister:

                break;
        }
    }

    @Override
    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {
        AfterReceived(jsonObject, output);
    }

    public void AfterReceived(JSONObject jsonObj, String result) {
        if (jsonObj != null) {
            try {
                EmployeeModel userDetail = EmployeeModel.getInstance();
                JSONObject json = new JSONObject(result);

                userDetail.setToken(String.valueOf(jsonObj.getString("token")));
                jsonObj = json.getJSONObject("patner");
                userDetail.setEmpId(String.valueOf(jsonObj.getString("empId")));
                userDetail.setUserType(String.valueOf(jsonObj.getString("userType")));
                userDetail.setFullName(String.valueOf(jsonObj.getString("fullName")));
                userDetail.setContactNo(String.valueOf(jsonObj.getString("contactNo")));
                userDetail.setEmailId(String.valueOf(jsonObj.getString("emailId")));
                userDetail.setUserRole(String.valueOf(jsonObj.getString("userRole")));
                userDetail.setAdhaarNo(String.valueOf(jsonObj.getString("adhaarNo")));
                userDetail.setPanNo(String.valueOf(jsonObj.getString("panNo")));
                userDetail.setAddress1(String.valueOf(jsonObj.getString("address1")));
                userDetail.setAddress2(String.valueOf(jsonObj.getString("address2")));
                userDetail.setCity(String.valueOf(jsonObj.getString("city")));
                userDetail.setState(String.valueOf(jsonObj.getString("state")));
                userDetail.setCountry(String.valueOf(jsonObj.getString("country")));
                userDetail.setAttempt(String.valueOf(jsonObj.getString("attempt")));
                userDetail.setStatus(String.valueOf(jsonObj.getString("status")));

                try {
//
                    mydatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                    mydatabase.execSQL("DROP TABLE IF EXISTS Employeedetails");
                    mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Employeedetails(Token VARCHAR,EmployeeID VARCHAR,UserType VARCHAR,FullName VARCHAR," +
                            "ContactNo VARCHAR,EmailId VARCHAR,UserRole VARCHAR,AdhaarNo VARCHAR, PanNo VARCHAR," +
                            "Address1 VARCHAR,Address2 VARCHAR,City VARCHAR,State VARCHAR,Country VARCHAR,Attempt VARCHAR,Status VARCHAR);");
                    mydatabase.execSQL("INSERT INTO Employeedetails VALUES('" + userDetail.getToken() + "','" + userDetail.getEmpId()
                            + "','" + userDetail.getUserType()
                            + "','" + userDetail.getFullName().replace("'", "") + "','" + userDetail.getContactNo() + "','"
                            + userDetail.getEmailId() + "','" + userDetail.getUserRole().replace("'", "") + "','"
                            + userDetail.getAdhaarNo() + "','" + userDetail.getPanNo().replaceAll("'", "")
                            + "','" + userDetail.getAddress1().replace("'", "") + "','" + userDetail.getAddress2()
                            + "','" + userDetail.getCity() + "','" + userDetail.getState() + "','" + userDetail.getCountry()
                            + "','" + userDetail.getAttempt()
                            + "','" + userDetail.getStatus()
                            + "');");
//                    '" + result.replace("'", "") + "'

                } catch (SQLiteException se) {
                    Toast.makeText(this, "" + se.getMessage(), Toast.LENGTH_SHORT).show();
                }
//
                if (userDetail.getStatus().equalsIgnoreCase("active")) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(this, "Account Inactive", Toast.LENGTH_SHORT).show();
//
                }
//                }
            } catch (JSONException e) {
//                Toast.makeText(this, "@Login" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Please Login Again", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            } catch (Exception e) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        } else {
            if (result.contains("404")) {
                Toast.makeText(this, "User Not Found", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid Credentials..!!", Toast.LENGTH_SHORT).show();
            }// showSnack(false, );
        }
        pDialog.dismiss();
    }
}
















//    public static void isNetworkAvailable(final Handler handler, final int timeout) {
//        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
//        // the answer must be send before before within the 'timeout' (in milliseconds)
//
//        new Thread() {
//            private boolean responded = false;
//            @Override
//            public void run() {
//                // set 'responded' to TRUE if is able to connect with google mobile (responds fast)
//                new Thread() {
//                    @Override
//                    public void run() {
//                        HttpGet requestForTest = new HttpGet("http://m.google.com");
//                        try {
//                            new DefaultHttpClient().execute(requestForTest); // can last...
//                            responded = true;
//                        }
//                        catch (Exception e) {
//                        }
//                    }
//                }.start();
//
//                try {
//                    int waited = 0;
//                    while(!responded && (waited < timeout)) {
//                        sleep(100);
//                        if(!responded ) {
//                            waited += 100;
//                        }
//                    }
//                }
//                catch(InterruptedException e) {} // do nothing
//                finally {
//                    if (!responded) { handler.sendEmptyMessage(0); }
//                    else { handler.sendEmptyMessage(1); }
//                }
//            }
//        }.start();
//        Handler h = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//
//                if (msg.what != 1) { // code if not connected
//
//                } else { // code if connected
//
//                }
//            }
//        };
//    }