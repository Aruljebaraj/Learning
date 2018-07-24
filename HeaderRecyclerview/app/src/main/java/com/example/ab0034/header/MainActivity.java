package com.example.ab0034.header;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView Recyclerview;
    ArrayList<HeaderDto> headerlist;
    ArrayList<String> Delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Recyclerview = (RecyclerView) findViewById(R.id.Recyclerview);
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        Recyclerview.setLayoutManager(lm);
        header();
    }

    public void header() {
        headerlist = new ArrayList<>();
        Delivery = new ArrayList<>();
        HeaderDto dto;

        dto = new HeaderDto();
        dto.setName("Arul Jebaraj");
        dto.setStatus("Active");
        headerlist.add(dto);
        if (!Delivery.contains(dto.getStatus())) {

//            String val = dto.getStatus();
            Delivery.add(dto.getStatus());
        }
        dto = new HeaderDto();
        dto.setName("Aaron Jebarson");
        dto.setStatus("Active");
        headerlist.add(dto);
        if (!Delivery.contains(dto.getStatus())) {

//            String val = dto.getStatus();
            Delivery.add(dto.getStatus());
        }
        dto = new HeaderDto();
        dto.setName("Arul Jebaraj");
        dto.setStatus("Not Available");
        headerlist.add(dto);
        if (!Delivery.contains(dto.getStatus())) {

//            String val = dto.getStatus();
            Delivery.add(dto.getStatus());
        }
        Recyclerview.setAdapter(new RecyclerAdapter(this, getList()));

    }

    private List<TitleViewHolder.Title> getList() {
        List<TitleViewHolder.Title> list = new ArrayList<>();
        for (int j = 0; j < Delivery.size(); j++) {
            List<HeaderDto> subTitles = new ArrayList<>();
            String Dateval = String.valueOf(Delivery.get(j));
            for (int i = 0; i < headerlist.size(); i++) {
                HeaderDto modl = headerlist.get(i);
                if (modl.getStatus().equals(Dateval)) {
                    subTitles.add(modl);

                }
            }
            TitleViewHolder.Title model = new TitleViewHolder.Title(Dateval, subTitles);
            list.add(model);
        }
        return list;
    }

    public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder, MyViewHolder> {
        Context context;

        public RecyclerAdapter(Context context, List<? extends ExpandableGroup> groups) {
            super(groups);
            this.context = context;
        }

        @Override
        public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            return new TitleViewHolder(view);
        }

        @Override
        public MyViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subitem_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(MyViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
            HeaderDto p = ((TitleViewHolder.Title) group).getItems().get(childIndex);
            holder.TxtName.setText(p.Name);
            holder.Txtstatus.setText(p.Status);

        }

        @Override
        public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
            holder.setGenreTitle(context, group);
        }

    }
}
