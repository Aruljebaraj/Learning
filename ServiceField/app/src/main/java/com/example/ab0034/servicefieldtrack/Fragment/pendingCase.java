package com.example.ab0034.servicefieldtrack.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ab0034.servicefieldtrack.MainActivity;
import com.example.ab0034.servicefieldtrack.R;
import com.example.ab0034.servicefieldtrack.Tracking;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class pendingCase extends Fragment {
    RecyclerView RecycleNewReferrals;
    ArrayList<PendingDto> pendingDtos;
    SwipeRefreshLayout SwipeRefresh;
    int PendingReferralsCount;
    LinearLayout LnrNoPendingCases;

    public pendingCase() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_case, container, false);
        RecycleNewReferrals = (RecyclerView) view.findViewById(R.id.RecycleNewReferrals);
        RecycleNewReferrals.setHasFixedSize(true);
        SwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        LnrNoPendingCases = (LinearLayout) view.findViewById(R.id.LnrNoPendingCases);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        RecycleNewReferrals.setLayoutManager(llm);
        Pending();
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

    public void Pending() {
        pendingDtos = new ArrayList<>();
        PendingDto dto = new PendingDto();
        dto.CustomerName = "Murtuza Bhaiji";
        dto.Status = "Actioned";
        pendingDtos.add(dto);
        dto = new PendingDto();
        dto.CustomerName = "Murtuza Bhaiji";
        dto.Status = "Rejected";
        pendingDtos.add(dto);
        dto = new PendingDto();
        dto.CustomerName = "Murtuza Bhaiji";
        dto.Status = "Pending";
        pendingDtos.add(dto);
        PendingAdapter pendingAdapter = new PendingAdapter(pendingDtos, getActivity());
        RecycleNewReferrals.setAdapter(pendingAdapter);
    }

    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void messageFromParentFragmentToActivity(String NewReferralsCount);
    }

    public void emptyview() {
        PendingReferralsCount = (pendingDtos.size());
        if (PendingReferralsCount == 0) {
            mListener.messageFromParentFragmentToActivity(String.valueOf(PendingReferralsCount));
            RecycleNewReferrals.setVisibility(View.GONE);
            LnrNoPendingCases.setVisibility(View.VISIBLE);
        } else {
            mListener.messageFromParentFragmentToActivity(String.valueOf(PendingReferralsCount));
            Collections.reverse(pendingDtos);
            PendingAdapter dta = new PendingAdapter(pendingDtos, getActivity());
            dta = new PendingAdapter(pendingDtos, getActivity());
            RecycleNewReferrals.setAdapter(dta);
        }
    }

    public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {
        ArrayList<PendingDto> listofpendingreports;
        Context context;

        //        public int pendingReportsCount = listofpendingreports.size();
        public PendingAdapter(ArrayList<PendingDto> listofpendingreports, Context context) {
            this.listofpendingreports = listofpendingreports;
            this.context = context;
            //  loadDailyTaskArray();

        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView TxtCustomerName, TxtStatus, TxtReferenceID;
            public CardView CardViewPendingReportDetails;
            ImageView ImgStatus;

            public MyViewHolder(View v) {
                super(v);

                ImgStatus = (ImageView) v.findViewById(R.id.ImgStatus);
                TxtCustomerName = (TextView) v.findViewById(R.id.TxtCustomerName);
                TxtStatus = (TextView) v.findViewById(R.id.TxtStatus);
                CardViewPendingReportDetails = (CardView) v.findViewById(R.id.CardViewPendingReportDetails);
            }
        }

        @Override
        public PendingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_row, parent, false);
            PendingAdapter.MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final PendingAdapter.MyViewHolder holder, final int position) {
            final PendingDto dt = listofpendingreports.get(position);
//            final String ImgName = getResources().getResourceEntryName(dt.CategoryImage);

            try {
                holder.TxtCustomerName.setText(dt.CustomerName);
//                holder.TxtStatus.setText(dt.Status);
                holder.ImgStatus.setImageResource(dt.Status.equalsIgnoreCase("Actioned") ? R.drawable.actioned :
                        dt.Status.equalsIgnoreCase("Rejected") ? R.drawable.rejected : R.drawable.hollow_white_phone_call);
//                holder.TxtPatientName.setTypeface(RobotoRegular);
//                holder.TxtPatientName.setText(dt.getPatientName().replace("_", " "));
                holder.TxtStatus.setText(dt.Status.replace("1", "Actioned")
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

//
//                    i.putExtra("DoctorName", dt.getDoctorName());
//                        i.putExtra("ReferenceId", dt.getLeadNo());
//                        i.putExtra("PatientName", dt.getPatientName().replace("_", " "));
//                        i.putExtra("PatientContact", dt.getContactNumber());
//                        i.putExtra("LeadStatus", dt.getStatus());
//                        i.putExtra("From", "ClosedCase");
                        startActivity(i);
                    }
                });

            } catch (Exception e) {
                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public int getItemCount() {
            return listofpendingreports.size();
        }
    }

    class PendingDto {
        int ImgStatus;
        String CustomerName;
        String Status;
    }
}
