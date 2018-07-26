package com.example.arul.packageapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;
import com.example.arul.packageapp.Background.UrlAsync;
import com.example.arul.packageapp.Barcode.Barcode;
import com.example.arul.packageapp.Other.SpringyAdapterAnimationType;
import com.example.arul.packageapp.Other.SpringyAdapterAnimator;
import com.example.arul.packageapp.Model.EmployeeModel;
import com.example.arul.packageapp.Model.OrderDetailsModel;
import com.example.arul.packageapp.Model.ProductModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class OrderDetails extends AppCompatActivity implements View.OnClickListener, UrlAsync.AsyncResponse {
    ImageView ImgBack, ImgEdit;
    RecyclerView DetailList;
    ProgressBar ProgresssCircle;
    EmployeeModel employeeModel = EmployeeModel.getInstance();
    ProductModel productModel = new ProductModel();
    OrderDetailsModel orderDetailsModel = OrderDetailsModel.getInstance();
    ArrayList<ProductModel> OrderDetailList;
    Button BtnDone, BtnBarcode;
    OrderDetailAdapter dtb;
    TextView TxtCustomerName, TxtOrderId, TxtAddress, TxtTotalItem, TxtBag, TxtEditOrder;
    String CustomerName = "", OrderId = "", Address = "", Status = "", Totalitem, Bag, Packeditems = "NA", EditOrder = "";
    ProgressDialog dialog;
    LinearLayout Main;
    String DB_NAME = "Dailyfresh";
    String Token;
    int TotalAvailable = 0, TotalNotAvailable = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        ImgEdit = (ImageView) findViewById(R.id.ImgEdit);
        ProgresssCircle = (ProgressBar) findViewById(R.id.ProgresssCircle);
        TxtCustomerName = (TextView) findViewById(R.id.TxtCustomerName);
        TxtOrderId = (TextView) findViewById(R.id.TxtOrderId);
        TxtAddress = (TextView) findViewById(R.id.TxtAddress);
        TxtBag = (TextView) findViewById(R.id.TxtBag);
        TxtEditOrder = (TextView) findViewById(R.id.TxtEditOrder);
        BtnDone = (Button) findViewById(R.id.BtnDone);
        BtnBarcode = (Button) findViewById(R.id.BtnBarcode);
        TxtTotalItem = (TextView) findViewById(R.id.TxtTotalItem);
        Main = (LinearLayout) findViewById(R.id.Main);
        Bundle bundle = getIntent().getExtras();
        CustomerName = bundle.getString("CustomerName");
        TxtCustomerName.setText(CustomerName);
        OrderId = bundle.getString("OrderId");
        TxtOrderId.setText(OrderId);
        Totalitem = bundle.getString("PluCount");
        Address = bundle.getString("Address");
        Status = bundle.getString("Status");
        try {
            TxtBag.setText(bundle.getString("Bag"));

        } catch (Exception e) {
        }
        TxtAddress.setText(Address);
        DetailList = (RecyclerView) findViewById(R.id.RecycleDetail);
//        DetailList.setHasFixedSize(true);
        setData();
        LoadUrl();
        LinearLayoutManager llm = new LinearLayoutManager(this);
        DetailList.setLayoutManager(llm);
        ImgBack.setOnClickListener(this);
        ImgEdit.setOnClickListener(this);
        TxtEditOrder.setOnClickListener(this);
        BtnDone.setOnClickListener(this);
        BtnBarcode.setOnClickListener(this);
        BtnDone.setEnabled(false);
        BtnBarcode.setEnabled(false);
        BtnDone.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        BtnBarcode.setBackgroundColor(getResources().getColor(R.color.colorGrey));

//        if (Status.equalsIgnoreCase("Packed")) {
//            TxtEditOrder.setVisibility(View.VISIBLE);
//            BtnDone.setVisibility(View.GONE);
//
//            ImgEdit.setVisibility(View.GONE);
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    1.0f
//            );
//            BtnBarcode.setLayoutParams(param);
//
//        }
//        TxtEditOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditOrder = "EditOrder";
//                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                        0,
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        0.5f
//                );
//                param.setMarginEnd(20);
//                BtnBarcode.setPadding(5, 5, 5, 5);
//                BtnBarcode.setLayoutParams(param);
//                BtnDone.setVisibility(View.VISIBLE);
//
//
//                BtnDone.setPadding(5, 5, 5, 5);
////                BtnDone.setLayoutParams(param);
//
//                dtb = new OrderDetailAdapter(OrderDetailList, OrderDetails.this);
//                DetailList.setAdapter(dtb);
//            }
//        });

    }

    private void setData() {
        try {


            SQLiteDatabase mydatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            String sql = "SELECT Token FROM Employeedetails";
            Cursor dashData;
            dashData = mydatabase.rawQuery(sql, null);
            if (dashData.moveToFirst()) {
                Token = dashData.getString(0);

            }
            dashData.close();
        } catch (Exception e) {

            Toast.makeText(this, "Please Login Again", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(OrderDetails.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }

    private void LoadUrl() {
        StringBuilder Url = new StringBuilder();
        ProgresssCircle.setVisibility(View.VISIBLE);
        Url.append(getResources().getString(R.string.UrlPrefix)).append("values/").append("GetOrderDetail?id=").append(OrderId);
        new UrlAsync(OrderDetails.this, Url.toString(), OrderDetails.this, "OrderDetail", true, Token).execute("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {
        try {
            String SAles = jsonObject.getString("saleItems");
            JSONArray jArray = new JSONArray(SAles);
            OrderDetailList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                jsonObject = jArray.getJSONObject(i);
                ProductModel dt = new ProductModel();

//                dt.setCategoryId(String.valueOf(jsonObject.getString("category")));
                dt.setCategoryName(String.valueOf(jsonObject.getString("category")));
                dt.setProductId(String.valueOf(jsonObject.getString("itemId")));
                dt.setProductName(String.valueOf(jsonObject.getString("name")));
//              dt.ProductVariant  =  String.valueOf(jsonObject.getString("category"));
                dt.setProductCost(Double.parseDouble(jsonObject.getString("totalPrice")));
                dt.setProductQuantity(String.valueOf(jsonObject.getString("quantity")));
                dt.setWeight(String.valueOf(jsonObject.getString("weight")));
                dt.setMeasurement(String.valueOf(jsonObject.getString("measurement")));
                dt.setItemStatus(String.valueOf(jsonObject.getString("status")));

                OrderDetailList.add(dt);

            }

            ProgresssCircle.setVisibility(View.GONE);
            dtb = new OrderDetailAdapter(OrderDetailList, this);
            DetailList.setAdapter(dtb);
            TxtTotalItem.setText(String.valueOf(OrderDetailList.size()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImgBack:
                startActivity(new Intent(OrderDetails.this, MainActivity.class));
                finish();
                break;
            case R.id.ImgEdit:
                BagcountPopup(1);
                break;
            case R.id.BtnDone:
                int TotalPackedItems = 0;
                int unpackedItems = 0;
                int TotalItem = 0;
                for (int i = 0; i < OrderDetailList.size(); i++) {

                    if (OrderDetailList.get(i).getItemStatus().equalsIgnoreCase("NotAvailable")) {
                        unpackedItems++;

                    } else if (OrderDetailList.get(i).getItemStatus().equalsIgnoreCase("Available")) {
                        TotalPackedItems++;
                    }
                    TotalItem = unpackedItems + TotalPackedItems;
                }

//                Toast.makeText(this, "unpacked Items " + unpackedItems, Toast.LENGTH_SHORT).show();
                if (TotalItem == OrderDetailList.size()) {
                    // ShowDialogBox();
                    ConfirmDialog();
//                    BagcountPopup(0);
                } else {
                    Toast.makeText(this, "Some Items aren't packed!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.BtnBarcode:
                try {
                    if (TxtBag.getText().equals("")) {
                        Toast.makeText(this, "Set BagCount", Toast.LENGTH_SHORT).show();
                    } else {
                        String Data;
                        if (OrderId.length() == 4) {
                            Data = " " + String.valueOf(OrderId).replace("SO", "SO00");
                        } else if (OrderId.length() == 5) {
                            Data = " " + String.valueOf(OrderId).replace("SO", "SO0");
                        } else {
                            Data = " " + String.valueOf(OrderId);
                        }

                        Intent intent = new Intent(this, Barcode.class);
                        intent.putExtra("SaleId", Data);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

        }

    }

    public void ConfirmDialog() {
        final Dialog confirmdialog = new Dialog(this);
        confirmdialog.setContentView(R.layout.custom_dailogbox);
        Button BtnCancel, Btnok;
        BtnCancel = (Button) confirmdialog.findViewById(R.id.BtnCancel);
        Btnok = (Button) confirmdialog.findViewById(R.id.Btnok);
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmdialog.dismiss();
                confirmdialog.setCancelable(false);
            }
        });
        Btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmdialog.dismiss();
                BagcountPopup(0);
            }
        });
        confirmdialog.show();
    }

    public void BagcountPopup(final int value) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.bagdialog);
        final ProductModel productModel = new ProductModel();

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.BtnCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.BtnContinue);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.RadioGroup);
        final EditText EdtBag = (EditText) dialog.findViewById(R.id.EdtBag);
        final RadioButton RadioBtnOther = (RadioButton) dialog.findViewById(R.id.RadioBtnOther);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton RadioBtn = (RadioButton) dialog.findViewById(radioGroup.getCheckedRadioButtonId());
                EdtBag.setText(RadioBtn.getText());
                if (RadioBtnOther.isChecked() == true) {
                    EdtBag.setVisibility(View.VISIBLE);
                    radioGroup.setVisibility(View.GONE);
                }
                Toast.makeText(OrderDetails.this, RadioBtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });


        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.setCancelable(false);
            }
        });

        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bag = EdtBag.getText().toString();
//                productModel.setBagcount(Bag);
                if (Bag.equals("0")) {
                    Toast.makeText(OrderDetails.this, "invalid Bagcount", Toast.LENGTH_SHORT).show();
                } else {
                    if (Integer.parseInt(Bag) < 10) {
                        productModel.setBagcount("0" + Bag);
//                        Bag = "0" + Bag;
                        TxtBag.setText(productModel.getBagcount());

                    } else {
                        productModel.setBagcount(Bag);
                        TxtBag.setText(productModel.getBagcount());
                    }


                }

                dialog.dismiss();
                dialog.setCancelable(false);
                if (value == 0) {
                    new MyAsyncTask().execute();
                    Toast.makeText(OrderDetails.this, "Package ready for Delivery", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OrderDetails.this, MainActivity.class));
                }
            }
        });
        dialog.show();
    }


    public void Load() {
        StringBuilder Url = new StringBuilder();
        Url.append(getResources().getString(R.string.UrlPrefix)).append("Package/SaleStatus");
        new UrlAsync(OrderDetails.this, Url.toString(), OrderDetails.this, "OrderDetail", true, employeeModel.getToken()).execute("");
    }

    public class OrderDetailAdapter extends RecyclerSwipeAdapter<OrderDetailAdapter.MyViewHolder> {
        protected SwipeItemRecyclerMangerImpl mItemManger = new SwipeItemRecyclerMangerImpl(this);

        ArrayList<ProductModel> OrderDetailList;
        Context context;

        public OrderDetailAdapter(ArrayList<ProductModel> OrderDetailList, Context context) {
            this.OrderDetailList = OrderDetailList;
            this.context = context;
        }

        @Override
        public OrderDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetail_row, parent, false);
            OrderDetailAdapter.MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(final OrderDetailAdapter.MyViewHolder holder, final int position) {
            //            mAnimator.onSpringItemBind(holder.itemView, position);

            final ProductModel g = OrderDetailList.get(position);
            holder.setIsRecyclable(false);
            if (g.getProductName().equals("")) {
                holder.TxtProductName.setText("NA");
            } else {
                holder.TxtProductName.setText(g.getProductName());
            }

            holder.TxtProductCategory.setText(g.getCategoryName());
            holder.TxtQuantity.setText(" x " + g.getProductQuantity());
            try {
                Integer qunatity = Integer.valueOf(g.getProductQuantity());
                Integer weight = Integer.valueOf(g.getWeight());
                Integer total = qunatity * weight;
                if (g.getProductName().contains("dozen")) {
                    holder.TxtTotalWeight.setText("12");
                    holder.TxtWeight.setText("12");
                } else {
                    holder.TxtTotalWeight.setText(String.valueOf(total) + " " + g.getMeasurement().toLowerCase());
                    holder.TxtWeight.setText(" " + g.getWeight() + " " + g.getMeasurement().toLowerCase());
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


            holder.TxtNotAvailable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Product Not Available", Toast.LENGTH_SHORT).show();
//                    g.setItemStatus("NotAvailable");


                    for (int i = 0; i < OrderDetailList.size(); i++) {
                        if (OrderDetailList.get(i).getProductId().equalsIgnoreCase(g.getProductId())) {
                            OrderDetailList.get(i).setItemStatus("NotAvailable");
                            break;
                        }
                    }
//                    g.setStatus("NotAvailable");
                    TotalNotAvailable++;
//                    button();
                    enableDoneButton();
                    holder.LnrViewOrder.setBackgroundColor(getResources().getColor(R.color.Notselected));
                    holder.TxtProductName.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtQuantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.Quantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtTotalWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.txtCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtProductCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                    Snackbar snackbar = Snackbar.make(Main, g.getProductName() + " " + "Not Available!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    holder.swipeLayout.close(true, true);
//                    notifyDataSetChanged();

//                    removeItem(position);
                }
            });


            holder.TxtPacked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    g.setStatus("Packed");
                    TotalAvailable++;
//                    button();
                    enableDoneButton();
                    holder.LnrViewOrder.setBackgroundColor(getResources().getColor(R.color.selected));
                    holder.TxtProductName.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtQuantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.Quantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtTotalWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.txtCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtProductCategory.setTextColor(getResources().getColor(R.color.colorWhite));
//                    g.setItemStatus("Available");
//                    OrderDetailList.get(position).setItemStatus("Available");

                    for (int i = 0; i < OrderDetailList.size(); i++) {
                        if (OrderDetailList.get(i).getProductId().equalsIgnoreCase(g.getProductId())) {
                            OrderDetailList.get(i).setItemStatus("Available");
                            String status = OrderDetailList.get(i).getItemStatus();
                            Toast.makeText(context, "" + status + position, Toast.LENGTH_SHORT).show();
//                            notifyItemChanged(position);
                            break;
                        }
                    }

                    Snackbar snackbar = Snackbar.make(Main, g.getProductName() + " " + "Packed!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    holder.swipeLayout.close(true, true);
                }
            });
            for (int i = 0; i < OrderDetailList.size(); i++) {
                if (OrderDetailList.get(i).getProductId().equalsIgnoreCase(g.getProductId()) &&
                        g.getItemStatus().equalsIgnoreCase("Available")) {
                    holder.LnrViewOrder.setBackgroundColor(getResources().getColor(R.color.selected));
                    holder.TxtProductName.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtQuantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.Quantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtTotalWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.txtCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtProductCategory.setTextColor(getResources().getColor(R.color.colorWhite));

                } else if (OrderDetailList.get(i).getProductId().equalsIgnoreCase(g.getProductId()) &&
                        g.getItemStatus().equalsIgnoreCase("NotAvailable")) {
                    holder.LnrViewOrder.setBackgroundColor(getResources().getColor(R.color.colorRedish));
                    holder.TxtProductName.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtQuantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.Quantity.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtTotalWeight.setTextColor(getResources().getColor(R.color.colorWhite));
//                    holder.txtCategory.setTextColor(getResources().getColor(R.color.colorWhite));
                    holder.TxtProductCategory.setTextColor(getResources().getColor(R.color.colorWhite));

                }
            }
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return OrderDetailList.size();
        }


        public void removeItem(int position) {
            OrderDetailList.remove(position);
            notifyItemRemoved(position);
        }

        public void restoreItem(ProductModel item, int position) {
            OrderDetailList.add(position, item);
            notifyItemInserted(position);
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView TxtProductName, TxtProductCategory, TxtQuantity, TxtWeight, TxtTotalWeight, TxtNotAvailable, TxtPacked, Quantity, txtCategory;
            public RelativeLayout viewBackground, LnrViewOrder;
            LinearLayout LnrProduct, LnSwipe;
            SwipeLayout swipeLayout;

            public MyViewHolder(View itemView) {
                super(itemView);
                TxtProductName = (TextView) itemView.findViewById(R.id.TxtProductName);
                TxtProductCategory = (TextView) itemView.findViewById(R.id.TxtProductCategory);
//                txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
//                Quantity = (TextView) itemView.findViewById(R.id.Quantity);
                TxtQuantity = (TextView) itemView.findViewById(R.id.TxtQuantity);
                TxtWeight = (TextView) itemView.findViewById(R.id.TxtWeight);
                TxtTotalWeight = (TextView) itemView.findViewById(R.id.TxtTotalWeight);
                LnrViewOrder = (RelativeLayout) itemView.findViewById(R.id.LnrViewOrder);
                LnrProduct = (LinearLayout) itemView.findViewById(R.id.LnrProduct);
                TxtNotAvailable = (TextView) itemView.findViewById(R.id.TxtNotAvailable);
                TxtPacked = (TextView) itemView.findViewById(R.id.TxtPacked);
                LnrViewOrder = itemView.findViewById(R.id.LnrViewOrder);
                LnSwipe = itemView.findViewById(R.id.LnSwipe);
                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            }
        }
    }

    private void enableDoneButton() {
        int Total = (TotalAvailable + TotalNotAvailable);
        if (Total == (OrderDetailList.size())) {
            BtnDone.setEnabled(true);
            BtnDone.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            BtnBarcode.setBackgroundColor(getResources().getColor(R.color.colorDarkGrey));
            BtnBarcode.setEnabled(true);
        } else {
//            BtnDone.setBackgroundColor(getResources().getColor(R.color.colorGrey));
//            BtnBarcode.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }


    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub
            Avaiability();
            return null;
        }

        protected void onPostExecute(Double result) {

            Toast.makeText(getApplicationContext(), "command sent",
                    Toast.LENGTH_LONG).show();
            Load();
        }


    }

    public String Avaiability() {

        InputStream inputStream = null;
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://dailyfreshapi.cxengine.net/Api/Package/SaleStatus");
        String json = "";

        JSONArray array = new JSONArray();
        try {
            JSONObject object = new JSONObject();
            object.accumulate("OrderId", OrderId);
            object.accumulate("By", employeeModel.getEmpId());
            object.accumulate("Status", "Packed");
            object.accumulate("BagCount", Bag);
            for (int i = 0; i < OrderDetailList.size(); i++) {

                JSONObject objects = new JSONObject();
                objects.accumulate("ItemId", OrderDetailList.get(i).getProductId());
                objects.accumulate("ItemStatus", OrderDetailList.get(i).getItemStatus());
                array.put(objects);
            }
            object.accumulate("Items", array.toString());
//                String Test = String.valueOf((object));

            StringEntity se = new StringEntity(object.toString());

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return result;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        try {
            inputStream.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return result;
    }


}
