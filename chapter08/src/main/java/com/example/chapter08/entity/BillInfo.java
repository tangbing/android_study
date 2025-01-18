package com.example.chapter08.entity;

import androidx.annotation.NonNull;

public class BillInfo {
    public int id;
    public String date;
    public int type;
    public  double amount;
    public String remark;

    public static  final int BILL_TYPE_INCOME = 0;
    public static  final int BILL_TYPE_COST = 1;

    @NonNull
    @Override
    public String toString() {
        return "BillInfo{" +
                "id=" + id +
                "id=" + date + '\'' +
                "type=" + type +
                "amount=" + amount +
                "remark=" + remark + "}";
    }
}
