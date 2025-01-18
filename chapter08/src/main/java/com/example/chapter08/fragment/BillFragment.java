package com.example.chapter08.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.chapter08.R;
import com.example.chapter08.adapter.BillListAdapter;
import com.example.chapter08.database.BillDBHelper;
import com.example.chapter08.entity.BillInfo;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BillFragment extends Fragment {

    public static BillFragment newInstance(String mYearMonth) {
        BillFragment fragment = new BillFragment();
        Bundle bundle = new Bundle();
        bundle.putString("yearMonth", mYearMonth);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        ListView lv_bill = view.findViewById(R.id.lv_bill);

        BillDBHelper mDBHelper = BillDBHelper.getInstance(getContext());
        Bundle bundle = getArguments();
        String yearMonth = bundle.getString("yearMonth");
        Log.d("tb", yearMonth);

        List<BillInfo> billInfoList = mDBHelper.queryBill(yearMonth);
        BillListAdapter listAdapter = new BillListAdapter(getContext(), billInfoList);
        lv_bill.setAdapter(listAdapter);
        return view;
    }
}