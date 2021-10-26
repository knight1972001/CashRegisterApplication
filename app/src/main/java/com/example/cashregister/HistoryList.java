package com.example.cashregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryList extends AppCompatActivity {
    private ArrayList<SoldItem> soldItemList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();
    RecyclerView recyclerList;
    ItemRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_history_list);

        getSupportActionBar().hide(); //hide actionbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        soldItemList = this.getIntent().getExtras().getParcelableArrayList("soldItemList");
        itemList=this.getIntent().getExtras().getParcelableArrayList("itemList");
        if (savedInstanceState != null) {
            getDataFromSavedPage(savedInstanceState);
        }

        soldItemList.forEach(item -> {
            System.out.println(item.getDateSold());
        });
//        if(getIntent().hasExtra("bundle")) {

//            Bundle bundle = getIntent().getBundleExtra("bundle");
//            soldItemList = bundle.getParcelableArrayList("soldItemList");
//
//        }

        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemRecyclerAdapter(soldItemList, this, new ItemRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(SoldItem item) {
                goToSoldItemDetail(item);
            }
        });
        recyclerList.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("itemList", itemList);
        outState.putParcelableArrayList("soldItemList", soldItemList);

        super.onSaveInstanceState(outState);
    }

    public void getDataFromSavedPage(Bundle savedInstanceState) {
        soldItemList = savedInstanceState.getParcelableArrayList("soldItemList");
        itemList = savedInstanceState.getParcelableArrayList("itemList");
    }

    public void goToSoldItemDetail(SoldItem item) {
        System.out.println(item.dateSold);
        Intent myIntent = new Intent(this, SoldItemDetail.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtra("soldItemDetail", item);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    public void backFunction(View v){
        Intent myIntent = new Intent(this, ManagementPanel.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }
}