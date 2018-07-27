package com.example.ab0034.token;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ab0034.token.Background.UrlAsync;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Services extends AppCompatActivity implements UrlAsync.AsyncResponse {
    GridView ServiceGrid;
    ArrayList<ServiceDto> servicelist;
    private boolean PressedOnce;
    LinearLayout LinearEmpty;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        LinearEmpty = (LinearLayout) findViewById(R.id.LinearEmpty);
        ServiceGrid = (GridView) findViewById(R.id.ServiceGrid);
        LoadUrl();
    }

    private void LoadUrl() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading please wait..");
        String URl = getResources().getString(R.string.Url) + "GetServices";
        new UrlAsync(Services.this, URl, Services.this, "Load", false, "").execute("");
    }

    @Override
    public void onBackPressed() {
        try {
            startActivity(new Intent(this, Login.class));
            finish();
        } catch (Exception e) {
        }
//        if (PressedOnce) {
//            PressedOnce = false;
////                TxtHeaderName.setText("Dashboard");
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
////                super.onBackPressed();
//            PressedOnce = true;
//
//
//        } else {
////                finish();
//
//            final Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.custom_dailogbox);
//            Button BtnCancel, Btnok;
//            BtnCancel = (Button) dialog.findViewById(R.id.BtnCancel);
//            Btnok = (Button) dialog.findViewById(R.id.Btnok);
//            BtnCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    dialog.setCancelable(false);
//                }
//            });
//            Btnok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                    Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_HOME);
////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                            finish();
//                            System.exit(0);
//
//                }
//            });
//            dialog.show();
////            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
////                    .setTitleText("CONFIRM EXIT")
////                    .setContentText("Do you want to exit the app?")
////                    .setConfirmText("Okay")
////                    .setCancelText("Cancel")
////                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
////                        @Override
////                        public void onClick(SweetAlertDialog sweetAlertDialog) {
////                            Intent intent = new Intent(Intent.ACTION_MAIN);
////                            intent.addCategory(Intent.CATEGORY_HOME);
//////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            startActivity(intent);
////                            finish();
////                            System.exit(0);
////                        }
////                    })
////                    .show();

        ///       }
    }

    @Override
    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {
        try {
            Gson gson = new Gson();
            servicelist = gson.fromJson(output, new TypeToken<List<ServiceDto>>() {
            }.getType());
            ServiceGrid.setAdapter(new ServiceAdapter(this, servicelist));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            progressDialog.dismiss();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        if (output.equals("")) {
//            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
//            LinearEmpty.setVisibility(View.VISIBLE);
//            ServiceGrid.setVisibility(View.GONE);
//        } else {
//            Gson gson = new Gson();8286544
//            servicelist = gson.fromJson(output, new TypeToken<List<ServiceDto>>() {
//            }.getType());
//            ServiceGrid.setAdapter(new ServiceAdapter(this, servicelist));
//            LinearEmpty.setVisibility(View.GONE);
//            ServiceGrid.setVisibility(View.VISIBLE);
//        }

    }

    public class ServiceAdapter extends ArrayAdapter<ArrayList<ServiceDto>> {
        ArrayList<ServiceDto> serviceDtoArrayList;
        Context context;

        public ServiceAdapter(Context context, ArrayList<ServiceDto> serviceDtoArrayList) {
            super(context, R.layout.service_row);
            this.context = context;
            this.serviceDtoArrayList = serviceDtoArrayList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_row, parent, false);
            TextView TxtService = (TextView) view.findViewById(R.id.TxtService);
            final ServiceDto dto = serviceDtoArrayList.get(position);
            TxtService.setText(dto.serviceName);
            final String service = dto.serviceName;


            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    new AlertDialog.Builder(Services.this)
//                            .setTitle(" Alert !! ")
//                            .setMessage("Would you like to generate appointment for " + " " + dto.serviceName)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(Services.this, AllServices.class)
                            .putExtra("ServiceId", dto.id));
                    Toast.makeText(context, "" + dto.serviceName, Toast.LENGTH_SHORT).show();
                }

//                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // Toast.makeText(context, "Make sure", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();

            });

            return view;
        }

        @Override
        public int getCount() {
            return serviceDtoArrayList.size();
        }
    }

}

class ServiceDto {
    String id;
    String serviceName;
    String priority;
    String postServiceSurvey;
    String description;
    String createdBy;
    String createdOn;
    String updatedBy;
    String updatedOn;
}
