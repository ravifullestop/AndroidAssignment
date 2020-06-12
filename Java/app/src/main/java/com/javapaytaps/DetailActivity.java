package com.javapaytaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.javapaytaps.adpter.DetailAdapter;
import com.javapaytaps.databinding.ActivityDetailBinding;
import com.javapaytaps.model.DetailModel;
import com.javapaytaps.model.DataModel;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding view;
    Context context = this;
    ArrayList<DetailModel> arrayList = new ArrayList<>();
    DataModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setOnClickListeners();
        showDetail();
    }

    /**
     * This method is used to set click listeners on views
     */
    private void setOnClickListeners() {
        view.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /***
     * This method is used to get data from the previous activity
     */
    private void showDetail() {
        Intent in = getIntent();
        model = (DataModel) in.getSerializableExtra("model");
        if (model != null) {
            arrayList.add(new DetailModel("Amount:-", model.getAmount()));
            arrayList.add(new DetailModel("Order ID:-", model.getOrderID()));
            arrayList.add(new DetailModel("Transaction Id:-", model.getTransactionId()));
            arrayList.add(new DetailModel("Token:-", model.getToken()));
            arrayList.add(new DetailModel("Transaction by:-", model.getTransactionTitle()));
            arrayList.add(new DetailModel("Email:-", model.getEmail()));
            arrayList.add(new DetailModel("Number:-", model.getNumber()));

            view.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            view.recyclerView.setAdapter(new DetailAdapter(context, arrayList));

        }
    }
}
