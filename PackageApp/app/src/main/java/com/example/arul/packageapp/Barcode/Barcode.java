package com.example.arul.packageapp.Barcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arul.packageapp.Model.OrderDetailsModel;
import com.example.arul.packageapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class Barcode extends AppCompatActivity {
    OrderDetailsModel orderDetailsModel = OrderDetailsModel.getInstance();
    Bitmap bitmap;
    String SaleId = "";
    ImageView img_Qr;
    TextView TxtBarcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        this.setFinishOnTouchOutside(true);
        img_Qr = (ImageView) findViewById(R.id.img_Qr);
//        TxtBarcode = (TextView) findViewById(R.id.TxtBarcode);


        SaleId = getIntent().getExtras().getString("SaleId");
//        SaleId =  orderDetailsModel.getBarcodeData();

        String Data = String.valueOf(SaleId);
        Code128 barcode = new Code128(this);
        barcode.setData(Data);
        barcode.setProcessTilde(false);
        bitmap = barcode.getBitmap(1000, 300);
        img_Qr.setImageBitmap(bitmap);
    }


}

