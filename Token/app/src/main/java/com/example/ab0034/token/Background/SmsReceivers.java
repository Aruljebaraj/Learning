package com.example.ab0034.token.Background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by AB0011 on 10/07/2017.
 */

public class SmsReceivers extends BroadcastReceiver {
    // final SmsManager sms = SmsManager.getDefault();
    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        try {
            Object[] pdus = (Object[]) data.get("pdus");

            for (int i = 0; i < pdus.length; i++) {//
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String sender = smsMessage.getDisplayOriginatingAddress();
                //You must check here if the sender is your provider and not another one with same text.

                String messageBody = smsMessage.getMessageBody();

                //Pass on the text to our listener.
                if (sender.equalsIgnoreCase("IM-MYTOKN") || sender.equalsIgnoreCase("HP-MYTOKN")) {
                    mListener.messageReceived(messageBody);
                } else {
                    Toast.makeText(context, "Please enter OTP manually", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
              Toast.makeText(context, "@otp "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}