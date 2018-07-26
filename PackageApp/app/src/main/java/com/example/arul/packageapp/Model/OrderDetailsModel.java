package com.example.arul.packageapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderDetailsModel implements Parcelable {


    public static OrderDetailsModel INSTANCE = null;
    String SalesOrderId;
    String CustomerId;
    String SalesPerson;
    String PlU_Count;
    String TotalQuantity;
    String TotalWeight;
    String TotalPrice;
    String Discount;
    String OrderdStatus;
    String PaymentStatus;
    String DeliveryDate;
    String Comment;
    String CreatedBy;
    String CreatedOn;
    String LastUpdatedBy;
    String LastUpdatedOn;
    String Name;
    String Address;
    String ContactNo;
    String QuantityValue;
    String Measurement;
    String Weight;
    String SaleItems;
    String BarcodeData;
    String Bagcount;
    String SlotId;
    String SlotType = "Header";
    String SlotTime;

    // other instance variables can be here
    public OrderDetailsModel(String salesOrderId, String customerId, String salesPerson, String plU_Count, String totalQuantity,
                             String totalWeight,
                             String totalPrice, String discount, String orderdStatus, String paymentStatus,
                             String deliveryDate, String comment,
                             String createdBy,
                             String createdOn,
                             String lastUpdatedBy,
                             String lastUpdatedOn,
                             String name,
                             String address,
                             String contactNo,
                             String quantityValue,
                             String measurement,
                             String weight,
                             String saleItems,
                             String barcodeData,
                             String status,
                             String bagcount,
                             String slotType,
                             String slotId,
                             String slotTime
    ) {

        SalesOrderId = salesOrderId;
        CustomerId = customerId;
        SalesPerson = salesPerson;
        PlU_Count = plU_Count;
        TotalQuantity = totalQuantity;
        TotalWeight = totalWeight;

        TotalPrice = totalPrice;
        Discount = discount;
        OrderdStatus = orderdStatus;
        PaymentStatus = paymentStatus;
        DeliveryDate = deliveryDate;
        Comment = comment;
        CreatedBy = createdBy;
        CreatedOn = createdOn;
        LastUpdatedBy = lastUpdatedBy;
        LastUpdatedOn = lastUpdatedOn;
        Name = name;
        Address = address;
        ContactNo = contactNo;
        QuantityValue = quantityValue;
        Measurement = measurement;
        Weight = weight;
        SaleItems = saleItems;
        BarcodeData = barcodeData;
        Bagcount = bagcount;
        SlotId = slotId;
        SlotType = slotType;
        SlotTime = slotTime;
    }

    public OrderDetailsModel() {
    }

    public static OrderDetailsModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new OrderDetailsModel();
        }
        return (INSTANCE);
    }

    public static OrderDetailsModel getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(OrderDetailsModel INSTANCE) {
        OrderDetailsModel.INSTANCE = INSTANCE;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getSalesOrderId() {
        return SalesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        SalesOrderId = salesOrderId;
    }

    public String getSalesPerson() {
        return SalesPerson;
    }

    public void setSalesPerson(String salesPerson) {
        SalesPerson = salesPerson;
    }

    public String getPlU_Count() {
        return PlU_Count;
    }

    public void setPlU_Count(String plU_Count) {
        PlU_Count = plU_Count;
    }

    public String getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getOrderdStatus() {
        return OrderdStatus;
    }

    public void setOrderdStatus(String orderdStatus) {
        OrderdStatus = orderdStatus;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return LastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        LastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedOn() {
        return LastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        LastUpdatedOn = lastUpdatedOn;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getQuantityValue() {
        return QuantityValue;
    }

    public void setQuantityValue(String quantityValue) {
        QuantityValue = quantityValue;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getSaleItems() {
        return SaleItems;
    }

    public void setSaleItems(String saleItems) {
        SaleItems = saleItems;
    }

    public String getBarcodeData() {
        return BarcodeData;
    }

    public void setBarcodeData(String barcodeData) {
        BarcodeData = barcodeData;
    }

    public String getBagcount() {
        return Bagcount;
    }

    public void setBagcount(String bagcount) {
        Bagcount = bagcount;
    }

    public String getSlotId() {
        return SlotId;
    }

    public void setSlotId(String slotId) {
        SlotId = slotId;
    }

    public String getSlotType() {
        return SlotType;
    }

    public void setSlotType(String slotType) {
        SlotType = slotType;
    }

    public String getSlotTime() {
        return SlotTime;
    }

    public void setSlotTime(String slotTime) {
        SlotTime = slotTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
