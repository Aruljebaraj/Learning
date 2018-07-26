package com.example.arul.packageapp.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arul.packageapp.Background.UrlAsync;
import com.example.arul.packageapp.Model.EmployeeModel;
import com.example.arul.packageapp.Model.OrderDetailsModel;
import com.example.arul.packageapp.MyViewHolder;
import com.example.arul.packageapp.OrderDetail_Packed;
import com.example.arul.packageapp.OrderDetails;
import com.example.arul.packageapp.Other.SpringyAdapterAnimationType;
import com.example.arul.packageapp.Other.SpringyAdapterAnimator;
import com.example.arul.packageapp.R;
import com.example.arul.packageapp.TitleViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackedList extends Fragment implements UrlAsync.AsyncResponse, SwipeRefreshLayout.OnRefreshListener {
    ProgressDialog dialog;
    RecyclerView PackedList;
    EmployeeModel employeeModel = EmployeeModel.getInstance();
    ArrayList<OrderDetailsModel> PackedListArray;
    RecyclerAdapter dtb;
    SwipeRefreshLayout SwipeRefresh;
    LinearLayout LnrNoCustomers;
    ArrayList<String> DeliveryDate;
    private int pendingCount;
    ArrayList<String> slots = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivery_list, container, false);
        PackedList = (RecyclerView) view.findViewById(R.id.PackedList);
        SwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        SwipeRefresh.setOnRefreshListener(this);
        PackedList.setHasFixedSize(true);
        LnrNoCustomers = (LinearLayout) view.findViewById(R.id.LnrNoCustomers);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        PackedList.setLayoutManager(llm);
        LoadPackedList();
        return view;
    }

    public void LoadPackedList() {
        SwipeRefresh.setRefreshing(true);
        try {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
            StringBuilder Url = new StringBuilder();
            Url.append(getResources().getString(R.string.UrlPrefix)).append("Package/").append("GetList?OrderType=").append("1");

            new UrlAsync(com.example.arul.packageapp.Fragment.PackedList.this, Url.toString(), getActivity(), "OrderedList", true, employeeModel.getToken()).execute("");

        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void ProcessFinish(String output, JSONObject jsonObject, String Identity, Boolean HasHeader, String Token) {
//        if (jsonObject != null) {
        try {
            PackedListArray = new ArrayList<>();
            DeliveryDate = new ArrayList<>();
            JSONArray jArray = new JSONArray(output);
            int count = 0;

            StringBuilder AllSlot = new StringBuilder();
            for (int i = 0; i < jArray.length(); i++) {
                jsonObject = jArray.getJSONObject(i);

                OrderDetailsModel dt = new OrderDetailsModel();
                dt.setSalesOrderId(String.valueOf(jsonObject.get("salesOrderId")));
                dt.setCustomerId(String.valueOf(jsonObject.getString("customerId")));
                dt.setSalesPerson(String.valueOf(jsonObject.getString("salesPerson")));
                dt.setPlU_Count(String.valueOf(jsonObject.getString("plU_Count")));
                dt.setTotalQuantity(String.valueOf(jsonObject.getString("totalQuantity")));
                dt.setTotalWeight(String.valueOf(jsonObject.getString("totalWeight")));

                dt.setTotalPrice(String.valueOf(jsonObject.getString("totalPrice")));
                dt.setDiscount(String.valueOf(jsonObject.getString("discount")));
                dt.setOrderdStatus(String.valueOf(jsonObject.getString("orderdStatus")));
                dt.setPaymentStatus(String.valueOf(jsonObject.getString("paymentStatus")));
                dt.setDeliveryDate(String.valueOf(jsonObject.getString("deliveryDate")));
                dt.setComment(String.valueOf(jsonObject.getString("comment")));
                dt.setCreatedBy(String.valueOf(jsonObject.getString("createdBy")));
                dt.setCreatedOn(String.valueOf(jsonObject.getString("createdOn")));
                dt.setLastUpdatedBy(String.valueOf(jsonObject.getString("lastUpdatedBy")));
                dt.setLastUpdatedOn(String.valueOf(jsonObject.getString("lastUpdatedOn")));
                dt.setName(String.valueOf(jsonObject.getString("name")));
                dt.setAddress(String.valueOf(jsonObject.getString("address")));
                dt.setContactNo(String.valueOf(jsonObject.getString("contactNo")));
                dt.setQuantityValue(String.valueOf(jsonObject.getString("quantityValue")));
                dt.setMeasurement(String.valueOf(jsonObject.getString("measurement")));
                dt.setWeight(String.valueOf(jsonObject.getString("weight")));
                dt.setSaleItems(String.valueOf(jsonObject.getString("saleItems")));

                if (Integer.parseInt(String.valueOf(jsonObject.getString("bags"))) < 10) {
                    dt.setBagcount("0" + String.valueOf(jsonObject.getString("bags")));
                } else {
                    dt.setBagcount(String.valueOf(jsonObject.getString("bags")));

                }

                dt.setSlotTime(String.valueOf(jsonObject.getString("slotDesc")));
                String val = jsonObject.getString("slotId");
                dt.setSlotId(val);
                if (!AllSlot.toString().contains(val))
                    AllSlot.append(val);
                AllSlot.append(",");

                //                dt.Type = count == 0 ? "Header" : "Data";
                if (!DeliveryDate.contains(dt.getDeliveryDate())) {
                    DeliveryDate.add(dt.getDeliveryDate());
                    if (!slots.contains(dt.getSlotId())) {
                        slots.add(dt.getSlotId());
                        count++;

                    }
                }else {
                    dt.setSlotType("Data");
                }

                PackedListArray.add(dt);
//                if (!DeliveryDate.contains(dt.getDeliveryDate())) {
//                    DeliveryDate.add(dt.getDeliveryDate());
//                }
            }

            try {
                int Count = 0;
                ArrayList<OrderDetailsModel> orderDetailsModels = new ArrayList<>();
                for (int i = 0; i < slots.size(); i++) {
                    String d = slots.get(i);
                    for (OrderDetailsModel s : PackedListArray) {
                        if (d.trim().equals(s.getSlotId())) {
                            orderDetailsModels.add(s);
                        }
                    }
                    OrderDetailsModel ss = orderDetailsModels.get(Count);
                    ss.setSlotType(d);
                    orderDetailsModels.set(Count, ss);
                    Count = orderDetailsModels.size();
                }
                PackedListArray = orderDetailsModels;
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();

            PackedList.setVisibility(View.VISIBLE);
            pendingCount = PackedListArray.size();
            if (pendingCount == 0) {
                PackedList.setVisibility(View.GONE);
                LnrNoCustomers.setVisibility(View.VISIBLE);
            } else {
                PackedList.setVisibility(View.VISIBLE);
                LnrNoCustomers.setVisibility(View.GONE);
//                Collections.reverse(PackedListArray);
                dtb = new RecyclerAdapter(getActivity(), getList());
                PackedList.setAdapter(dtb);
            }

            SwipeRefresh.setRefreshing(false);


        } catch (Exception je) {
            Toast.makeText(getActivity(), je.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    }


    private List<TitleViewHolder.Title> getList() {
        List<TitleViewHolder.Title> list = new ArrayList<>();
        for (int j = 0; j < DeliveryDate.size(); j++) {
            List<OrderDetailsModel> subTitles = new ArrayList<>();
            String Dateval = DeliveryDate.get(j);
            for (int i = 0; i < PackedListArray.size(); i++) {
                OrderDetailsModel modl = PackedListArray.get(i);
                if (modl.getDeliveryDate().equals(Dateval)) {
                    subTitles.add(modl);
                }
            }
            TitleViewHolder.Title model = new TitleViewHolder.Title(Dateval, subTitles);
            list.add(model);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        LoadPackedList();
    }

//    public class PackedAdapter extends RecyclerView.Adapter<MyViewHolder> {
//        ArrayList<OrderDetailsModel> ListofPackedList;
//        Context context;
//        private SpringyAdapterAnimator mAnimator;
//
//        public PackedAdapter(ArrayList<OrderDetailsModel> ListofPackedList, Context context) {
//            this.ListofPackedList = ListofPackedList;
//            this.context = context;
//            mAnimator = new SpringyAdapterAnimator(PackedList);
//            mAnimator.setSpringAnimationType(SpringyAdapterAnimationType.SLIDE_FROM_BOTTOM);
//            mAnimator.addConfig(85, 15);
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_row, parent, false);
//            mAnimator.onSpringItemCreate(view);
//            MyViewHolder viewHolder = new MyViewHolder(view);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder holder, int position) {
//            mAnimator.onSpringItemBind(holder.itemView, position);
//            final OrderDetailsModel dt = ListofPackedList.get(position);
//            holder.TxtCustomerName.setText(dt.getName());
//            holder.TxtTotalOrders.setText(dt.getPlU_Count());
//            holder.TxtOrderID.setText(dt.getSalesOrderId());
//            holder.RelViewOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent in = new Intent(getActivity(), OrderDetail_Packed.class);
//
//                    in.putExtra("CustomerName", dt.getName());
//                    in.putExtra("OrderId", dt.getSalesOrderId());
//                    in.putExtra("Address", dt.getAddress());
//                    in.putExtra("Status", dt.getOrderdStatus());
//                    in.putExtra("Bag", dt.getBagcount());
//                    startActivity(in);
//                    getActivity().finish();
//                }
//            });
//
//            if (dt.getSlotType().equals("Data")) {
//                holder.RelSlot.setVisibility(View.GONE);
//            } else {
//                holder.RelSlot.setVisibility(View.VISIBLE);
//
//                for (String s : slots) {
//                    if (s.equalsIgnoreCase(dt.getSlotId())) {
//                        holder.TxtSlot.setText(dt.getSlotId());
//                        if (dt.getSlotId().isEmpty()) {
//                            holder.TxtSlotTime.setText("General Time");
//                        } else {
//                            holder.TxtSlotTime.setText(dt.getSlotTime());
//                        }
//                        break;
//                    }
//                }
//            }
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return ListofPackedList.size();
//        }
//    }


    public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<TitleViewHolder, MyViewHolder> {

        Context context;

        public RecyclerAdapter(Context context, List<? extends ExpandableGroup> groups) {
            super(groups);
            this.context = context;

        }

        @Override
        public TitleViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header, parent, false);
            return new TitleViewHolder(view);
        }

        @Override
            public MyViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.package_row, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(MyViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            final OrderDetailsModel subTitle = ((TitleViewHolder.Title) group).getItems().get(childIndex);

            holder.TxtSlot.setText(subTitle.getSlotId());
            holder.TxtCustomerName.setText(subTitle.getName());
            holder.TxtTotalOrders.setText(subTitle.getPlU_Count());
            holder.TxtOrderID.setText(subTitle.getSalesOrderId());
            holder.TxtDeliveryDate.setText(subTitle.getDeliveryDate());
            holder.RelViewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(getActivity(), OrderDetail_Packed.class);
                    in.putExtra("CustomerName", subTitle.getName());
                    in.putExtra("OrderId", subTitle.getSalesOrderId());
                    in.putExtra("Address", subTitle.getAddress());
                    in.putExtra("Status", subTitle.getOrderdStatus());
                    in.putExtra("PluCount", subTitle.getPlU_Count());
                    startActivity(in);
                    getActivity().finish();
                    dialog.show();
                }
            });
            if (subTitle.getSlotType().equals("Data")) {
                holder.RelSlot.setVisibility(View.GONE);
            } else {
                holder.RelSlot.setVisibility(View.VISIBLE);

                for (String s : slots) {
                    if (s.equalsIgnoreCase(subTitle.getSlotId())) {
                        holder.TxtSlot.setText(subTitle.getSlotId());
                        if (subTitle.getSlotId().isEmpty()) {
                            holder.TxtSlotTime.setText("General Time");
                        } else {
                            holder.TxtSlotTime.setText(subTitle.getSlotTime());
                        }
                        break;
                    }
                }
            }

        }

        @Override
        public void onBindGroupViewHolder(TitleViewHolder holder, int flatPosition, ExpandableGroup group) {
            holder.setGenreTitle(context, group);
        }


    }
}
