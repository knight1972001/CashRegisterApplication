package com.example.cashregister;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDateTime;
import java.util.Date;

public class SoldItem implements Parcelable {
    private String name;
    private double total;
    private int quantity;
    public String dateSold;

    public SoldItem(String name, int quantity, double total, String dateSold) {
        this.name = name;
        this.quantity = quantity;
        this.total = total;
        this.dateSold = dateSold;
    }


    protected SoldItem(Parcel in) {
        name = in.readString();
        total = in.readDouble();
        quantity = in.readInt();
        dateSold = in.readString();
    }

    public static final Creator<SoldItem> CREATOR = new Creator<SoldItem>() {
        @Override
        public SoldItem createFromParcel(Parcel in) {
            return new SoldItem(in);
        }

        @Override
        public SoldItem[] newArray(int size) {
            return new SoldItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeDouble(total);
        parcel.writeInt(quantity);
        parcel.writeString(dateSold);
    }

    public String getName() { return this.name;};
    public int getQuantity() { return this.quantity;};
    public double getTotal() { return this.total;};
    public String getDateSold() { return "Purchased day: "+ this.dateSold;};
}