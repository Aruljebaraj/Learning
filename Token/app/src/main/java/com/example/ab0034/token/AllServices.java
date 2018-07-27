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

public class AllServices extends AppCompatActivity implements UrlAsync.AsyncResponse {
    GridView ServiceGrid;
    ArrayList<QueueDto> servicelist;
    private boolean PressedOnce;
    String ServiceId = "", ServiceName = "";
    Global g = Global.getInstance();
    LinearLayout LinearEmpty;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allservices);
        LinearEmpty = (LinearLayout) findViewById(R.id.LinearEmpty);

        ServiceGrid = (GridView) findViewById(R.id.ServiceGrid);
        try {
            if (getIntent() != null) {
                ServiceId = getIntent().getStringExtra("ServiceId");

                LoadUrl(ServiceId, getResources().getString(R.string.BranchId));
            }
        } catch (Exception e) {
            Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
        }

    }

    private void LoadUrl(String SID, String BID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading please wait..");
        String URl = getResources().getString(R.string.Url) + "GetQueues/{SId}/{BId}".replace("{SId}", SID).replace("{BId}", BID);
        new UrlAsync(AllServices.this, URl, AllServices.this, "Load", false, "").execute("");
    }

    private void AppointUrl(String QID, String BID) {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setMessage("Loading please wait..");
        String URl = getResources().getString(R.string.Url) + "MakeAppointment/{Name}/{ContactNo}/{QueueId}/{BranchId}".replace("{Name}", g.getName())
                .replace("{ContactNo}", g.getContact()).replace("{QueueId}", QID).replace("{BranchId}", BID);
        new UrlAsync(AllServices.this, URl, AllServices.this, "Create", false, "").execute("");
    }


//    @Override
//    public void onBackPressed() {
//
//        if (PressedOnce) {
//            PressedOnce = false;
////                TxtHeaderName.setText("Dashboard");
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
////                super.onBackPressed();
//            PressedOnce = true;
//
//
////        } else {
//////                finish();
////
////            final Dialog dialog = new Dialog(this);
////            dialog.setContentView(R.layout.custom_dailogbox);
////            Button BtnCancel, Btnok;
////            BtnCancel = (Button) dialog.findViewById(R.id.BtnCancel);
////            Btnok = (Button) dialog.findViewById(R.id.Btnok);
////            BtnCancel.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    dialog.dismiss();
////                    dialog.setCancelable(false);
////                }
////            });
////            Btnok.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    dialog.dismiss();
////                    Intent intent = new Intent(Intent.ACTION_MAIN);
////                    intent.addCategory(Intent.CATEGORY_HOME);
//////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                    startActivity(intent);
////                    finish();
////                    System.exit(0);
////
////                }
////            });
////            dialog.show();
//////            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//////                    .setTitleText("CONFIRM EXIT")
//////                    .setContentText("Do you want to exit the app?")
//////                    .setConfirmText("Okay")
//////                    .setCancelText("Cancel")
//////                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//////                        @Override
//////                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//////                            Intent intent = new Intent(Intent.ACTION_MAIN);
//////                            intent.addCategory(Intent.CATEGORY_HOME);
////////                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//////                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////                            startActivity(intent);
//////                            finish();
//////                            System.exit(0);
//////                        }
//////                    })
//////                    .show();
////
////        }
//    }

    @Override
    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {

        if (Identity.equals("Load")) {
            if (output.equals("[]")) {
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
                LinearEmpty.setVisibility(View.VISIBLE);
                ServiceGrid.setVisibility(View.GONE);
            } else {
                Gson gson = new Gson();
                servicelist = gson.fromJson(output, new TypeToken<List<QueueDto>>() {
                }.getType());
                ServiceGrid.setAdapter(new ServiceAdapter(this, servicelist));

            }

        } else {
            if (output.contains("MT")) {
                startActivity(new Intent(AllServices.this, Success.class)
                        .putExtra("Service", ServiceName).putExtra("ServiceId", output));
                Toast.makeText(AllServices.this, ServiceName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }
        progressDialog.dismiss();

    }

    class ServiceAdapter extends ArrayAdapter<ArrayList<QueueDto>> {
        ArrayList<QueueDto> serviceDtoArrayList;
        Context context;

        public ServiceAdapter(Context context, ArrayList<QueueDto> serviceDtoArrayList) {
            super(context, R.layout.service_row);
            this.context = context;
            this.serviceDtoArrayList = serviceDtoArrayList;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_row, parent, false);
            TextView TxtService = (TextView) view.findViewById(R.id.TxtService);
            final QueueDto dto = serviceDtoArrayList.get(position);
            TxtService.setText(dto.queueName);
            ServiceName = dto.queueName;


            view.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
//                    new AlertDialog.Builder(AllServices.this)
//                            .setTitle(" Alert!!")
//                            .setMessage("Appointment will be generated for " + " " + dto.queueName)
//                            .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
                   try {
                       AppointUrl(dto.id, AllServices.this.getResources().getString(R.string.BranchId));
                   }catch (Exception e){
                       Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
//                  try {
//                       progressDialog.dismiss();
//                  }catch (Exception e){
//                      Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                  }
//                                }
//                            })
//                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
////                                    Toast.makeText(context, "Make sure", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return serviceDtoArrayList.size();
        }
    }

}

class QueueDto {
    String id;
    String queueName;
    String branch;
    String service;
    String averageHandleTime;
    String copyAHTFlag;
    String rejectNewAppointmentFlag;
    String moveAppointmentFlag;
    String buffer;
    String createdBy;
    String createdOn;
    String updatedBy;
    String updatedOn;
    String status;
}
