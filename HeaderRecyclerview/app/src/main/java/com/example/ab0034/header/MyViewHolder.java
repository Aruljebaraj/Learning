package com.example.ab0034.header;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class MyViewHolder extends ChildViewHolder {
    TextView TxtName, Txtstatus;

    public MyViewHolder(View itemView) {
        super(itemView);
        TxtName = (TextView) itemView.findViewById(R.id.TxtName);
        Txtstatus = (TextView) itemView.findViewById(R.id.TxtStatus);
    }


}