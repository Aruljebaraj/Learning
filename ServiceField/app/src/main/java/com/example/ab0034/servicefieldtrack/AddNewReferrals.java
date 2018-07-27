package com.example.ab0034.servicefieldtrack;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ab0034.servicefieldtrack.MaterialSpinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewReferrals extends AppCompatActivity implements View.OnClickListener {
    LinearLayout LnrAddLead;
    List<String> cityList = Arrays.asList("Mumbai", "Chennai");
    List<String> LanguageList = Arrays.asList("English", "Hindi", "Tamil", "Marathi", "Malayalam");
    List<String> LeadList = Arrays.asList("Direct", "Roadshow", "One to One", "Event");
    MaterialSpinner SpinnerCity, SpinnerLanguage, SpinnerLead;
    ImageView ImgBack;
    Button BtnSubmit;
    EditText EdtCustomerName, EdtExtension, EdtContactNumber, EdtComment, EdtAddress, EdtCountry, EdtState, EdtZip;
    Snackbar snack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_referrals);
        LnrAddLead = (LinearLayout) findViewById(R.id.LnrAddLead);
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        EdtCustomerName = (EditText) findViewById(R.id.EdtCustomerName);
        EdtExtension = (EditText) findViewById(R.id.EdtExtension);
        EdtContactNumber = (EditText) findViewById(R.id.EdtContactNumber);
        EdtComment = (EditText) findViewById(R.id.EdtComment);
        EdtAddress = (EditText) findViewById(R.id.EdtAddress);
        EdtCountry = (EditText) findViewById(R.id.EdtCountry);
        EdtState = (EditText) findViewById(R.id.EdtState);
        EdtZip = (EditText) findViewById(R.id.EdtZip);
        BtnSubmit = (Button) findViewById(R.id.BtnSubmit);
        SpinnerCity = (MaterialSpinner) findViewById(R.id.SpinnerCity);
        SpinnerLanguage = (MaterialSpinner) findViewById(R.id.SpinnerLanguage);
        SpinnerLead = (MaterialSpinner) findViewById(R.id.SpinnerLead);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, cityList);
        ArrayAdapter<String> LanguageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LanguageList);
        ArrayAdapter<String> LeadAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LeadList);
        SpinnerCity.setAdapter(arrayAdapter);
        SpinnerLanguage.setAdapter(LanguageAdapter);
        SpinnerLead.setAdapter(LeadAdapter);
        ImgBack.setOnClickListener(this);
        BtnSubmit.setOnClickListener(this);

    }

    public final static boolean validateLetters(String txt) {

        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();

    }

    public String validate(String name, String contact, String Country, String State, String Zip, String Address) {
        String valid = "NA";

        valid = (!validateLetters(name)) ? "Patient name must contain only alphabets" : valid;
//        valid = (comments.isEmpty()) ? "Enter comments" : valid;


        valid = (Zip.isEmpty()) ? "Enter ZipCode" : valid;
        valid = (Country.isEmpty()) ? "Enter Country" : valid;
        valid = (State.isEmpty()) ? "Enter State" : valid;
        valid = (Address.isEmpty()) ? "Enter Address" : valid;
        valid = (contact.isEmpty() || contact.length() > 10 || contact.length() < 10) ? "Enter a valid contact number" : valid;
        valid = (name.isEmpty()) ? "Enter patient name" : valid;
//        valid = (contact.length() > 10 || contact.length() < 10) ? "Enter a valid contact number" : valid;
//        valid = (prescriptionImages == 0) ? "Upload images" : valid;
        valid = (name.isEmpty() && contact.isEmpty() && Country.isEmpty() && Address.isEmpty() && State.isEmpty() && Zip.isEmpty()) ? "Enter all details" : valid;
        return valid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImgBack:
//                startActivity(new Intent(this, MainActivity.class));
                onBackPressed();

                break;
            case R.id.BtnSubmit:
                final String name = EdtCustomerName.getText().toString();
                final String contact = EdtContactNumber.getText().toString();
                final String Country = EdtCountry.getText().toString();
                final String Address = EdtAddress.getText().toString();
                final String State = EdtState.getText().toString();
//                final String comments = EdtComment.getText().toString();
                final String Zip = EdtZip.getText().toString();

                String value = validate(name, contact, Country, State, Zip, Address);

                if (value.equals("NA")) {

                } else if (value.equals("Enter patient name")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Patient name must contain only alphabets")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter a valid contact number")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter comments")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter Country")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter State")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter ZipCode")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter Address")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                } else if (value.equals("Enter all details")) {
                    snack = Snackbar
                            .make(LnrAddLead, "" + value, Snackbar.LENGTH_LONG);
                    snack.show();
                }
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
}
