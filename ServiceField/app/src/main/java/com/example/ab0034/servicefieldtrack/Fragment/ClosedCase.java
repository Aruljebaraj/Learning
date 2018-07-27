package com.example.ab0034.servicefieldtrack.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ab0034.servicefieldtrack.MainActivity;
import com.example.ab0034.servicefieldtrack.R;
import com.example.ab0034.servicefieldtrack.Tracking;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClosedCase extends Fragment {
    RecyclerView RecycleClosedReport;
    ArrayList<ClosedDto> ClosedCase;
    SwipeRefreshLayout SwipeRefresh;
    int PendingReferralsCount;
    LinearLayout LnrNoClosedCases;

    public ClosedCase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_closed_case, container, false);
        RecycleClosedReport = (RecyclerView) view.findViewById(R.id.RecycleClosedReport);
        SwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        LnrNoClosedCases = (LinearLayout) view.findViewById(R.id.LnrNoPendingCases);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        RecycleClosedReport.setLayoutManager(llm);
        Closedcase();
        SwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startActivity(new Intent(getActivity(), MainActivity.class));
                SwipeRefresh.setRefreshing(false);
            }
        });
        emptyview();
        return view;
    }

    public void Closedcase() {
        ClosedCase = new ArrayList<>();
        ClosedDto dto;
        dto = new ClosedDto();
        dto.CustomerName = "Riyaz Shaikh";
        dto.Status = "Closed";
        dto.ImgStatus = R.drawable.actioned;
        ClosedCase.add(dto);
    }

    private OnChildFragmentToActivityInteractionListener mActivityListener;

    public interface OnChildFragmentToActivityInteractionListener {
        void messageFromChildFragmentToActivity(String PendingReferralsCount);
    }

    public void emptyview() {
        PendingReferralsCount = (ClosedCase.size());
        if (PendingReferralsCount == 0) {
            mActivityListener.messageFromChildFragmentToActivity(String.valueOf(PendingReferralsCount));
            RecycleClosedReport.setVisibility(View.GONE);
            LnrNoClosedCases.setVisibility(View.VISIBLE);
        } else {
            mActivityListener.messageFromChildFragmentToActivity(String.valueOf(PendingReferralsCount));
            Collections.reverse(ClosedCase);
            ClosedAdapter dta = new ClosedAdapter(ClosedCase, getActivity());
            dta = new ClosedAdapter(ClosedCase, getActivity());
            RecycleClosedReport.setAdapter(dta);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // check if Activity implements listener
        if (context instanceof OnChildFragmentToActivityInteractionListener) {
            mActivityListener = (OnChildFragmentToActivityInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChildFragmentToActivityInteractionListener");
        }
    }

    class ClosedAdapter extends RecyclerView.Adapter<ClosedAdapter.MyViewHolder> {
        ArrayList<ClosedDto> closedDtoArrayList;
        Context context;

        public ClosedAdapter(ArrayList<ClosedDto> closedDtoArrayList, Context context) {
            this.closedDtoArrayList = closedDtoArrayList;
            this.context = context;
        }

        @Override
        public ClosedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.closed_row, parent, false);
            ClosedAdapter.MyViewHolder vh = new MyViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ClosedAdapter.MyViewHolder holder, int position) {
            ClosedDto p = closedDtoArrayList.get(position);
            holder.TxtCustomerName.setText(p.CustomerName);
//                holder.TxtStatus.setText(dt.Status);
            holder.ImgStatus.setImageResource(p.Status.equalsIgnoreCase("Actioned") ? R.drawable.actioned :
                    p.Status.equalsIgnoreCase("Rejected") ? R.drawable.rejected : R.drawable.hollow_white_phone_call);
//                holder.TxtPatientName.setTypeface(RobotoRegular);
//                holder.TxtPatientName.setText(dt.getPatientName().replace("_", " "));
            holder.TxtStatus.setText(p.Status.replace("1", "Actioned")
                    .replace("2", "Rejected")
                    .replace("3", "Scheduled Callback")
                    .replace("4", "Attending")
                    .replace("5", "In-Process"));
//                holder.TxtStatus.setTypeface(RobotoLight);

//
            holder.CardViewPendingReportDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), Tracking.class);
//                    i.putExtra("DoctorName", dt.getDoctorName());
//                        i.putExtra("ReferenceId", dt.getLeadNo());
//                        i.putExtra("PatientName", dt.getPatientName().replace("_", " "));
//                        i.putExtra("PatientContact", dt.getContactNumber());
//                        i.putExtra("LeadStatus", dt.getStatus());
//                        i.putExtra("From", "ClosedCase");
                    startActivity(i);
                }
            });

        }

        @Override
        public int getItemCount() {
            return closedDtoArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView TxtCustomerName, TxtStatus, TxtReferenceID;
            public CardView CardViewPendingReportDetails;
            ImageView ImgStatus;

            public MyViewHolder(View itemView) {
                super(itemView);
                ImgStatus = (ImageView) itemView.findViewById(R.id.ImgStatus);
                TxtCustomerName = (TextView) itemView.findViewById(R.id.TxtCustomerName);
                TxtStatus = (TextView) itemView.findViewById(R.id.TxtStatus);
                CardViewPendingReportDetails = (CardView) itemView.findViewById(R.id.CardViewPendingReportDetails);
            }
        }
    }

    class ClosedDto {
        int ImgStatus;
        String CustomerName;
        String Status;
    }
}
