package com.example.arul.packageapp;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class MyViewHolder extends ChildViewHolder {

    public TextView TxtCustomerName, TxtTotalOrders, TxtOrderID, TxtSlot, TxtSlotTime, TxtDeliveryDate;
    public RelativeLayout RelViewOrder, RelSlot;

    public MyViewHolder(View v) {
        super(v);

        TxtSlot = (TextView) v.findViewById(R.id.TxtSlot);
        TxtSlotTime = (TextView) v.findViewById(R.id.TxtSlotTime);
        TxtCustomerName = (TextView) v.findViewById(R.id.TxtCustomerName);
        TxtTotalOrders = (TextView) v.findViewById(R.id.TxtTotalOrders);
        TxtOrderID = (TextView) v.findViewById(R.id.TxtOrderID);
        TxtDeliveryDate = (TextView) v.findViewById(R.id.TxtDeliveryDate);
        RelViewOrder = (RelativeLayout) v.findViewById(R.id.RelViewOrder);
        RelSlot = (RelativeLayout) v.findViewById(R.id.RelSlot);

    }
}