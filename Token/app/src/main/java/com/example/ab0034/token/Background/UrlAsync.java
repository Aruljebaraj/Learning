package com.example.ab0034.token.Background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by AB0019 on 08-03-2018.
 */

public class UrlAsync extends AsyncTask<String, Void, String> {
    ProgressDialog dialog;
    JSONObject jsonObj;
    Context context;
    Boolean HasHeader;
    String url, Identity, Token;
    public AsyncResponse delegate = null;

    public UrlAsync(Context context) {
        this.context = context;
    }

    public interface AsyncResponse {
        void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token);
    }

    public UrlAsync(AsyncResponse delegate, String url, Context context, String Identity, Boolean HasHeader, String Token) {
        this.delegate = delegate;
        this.url = url;
        this.context = context;
        this.Identity = Identity;
        this.HasHeader = HasHeader;
        this.Token = Token;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpClient httpClient = new DefaultHttpClient();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            StringEntity se = new StringEntity(url.toString(), HTTP.UTF_8);
            se.setContentType("text/xml");
            HttpPost httpPost = new HttpPost(url.toString());
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Content-Type", "text/plain");
            if (HasHeader) {
                httpPost.setHeader("Authorization", "Bearer " + Token);
            }

            HttpResponse httpResponse = httpClient.execute(httpPost);
            InputStream inputStream = httpResponse.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String bufferedStrChunk = null;

            while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                stringBuilder.append(bufferedStrChunk);
            }
            try {
                jsonObj = new JSONObject(stringBuilder.toString().trim());
            } catch (Exception e) {

            }
            return stringBuilder.toString();
        } catch (ClientProtocolException cpe) {
            System.out.println("Protocol HttpResponese :" + cpe);
            stringBuilder.append("Failed to connect? ClientProtocolException").append(cpe.getMessage());
            // Toast.makeText(context, "Failed to connect? ClientProtocolException", Toast.LENGTH_SHORT).show();
            cpe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IO Exception HttpResponse :" + ioe);
            stringBuilder.append("Failed to connect? ClientProtocolException").append(ioe.getMessage());
            //Toast.makeText(context, "Failed to connect? IO Exception", Toast.LENGTH_SHORT).show();
            ioe.printStackTrace();
        } catch (Exception ignored) {
            stringBuilder.append("Failed to connect?").append(ignored.getMessage());
            //Toast.makeText(context, "Failed to connect? Exception", Toast.LENGTH_SHORT).show();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            delegate.ProcessFinish(result, jsonObj, Identity, false, "");
        } catch (Exception e) {
            Toast.makeText(context, "error Occurred" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}