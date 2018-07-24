package com.example.ab0034.header;

import android.os.Parcel;
import android.os.Parcelable;

public class HeaderDto implements Parcelable {

    private static volatile HeaderDto sSoleInstance = new HeaderDto();

    public HeaderDto() {
    }
    public static HeaderDto getInstance() {
        return sSoleInstance;
    }


    String Name;
    String Status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

}
