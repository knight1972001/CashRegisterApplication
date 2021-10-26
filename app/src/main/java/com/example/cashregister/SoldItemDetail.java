package com.example.cashregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SoldItemDetail extends AppCompatActivity {
    SoldItem soldItem;
    ArrayList<SoldItem> soldItemList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sold_item_detail);
        getSupportActionBar().hide(); //hide actionbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle data = getIntent().getExtras();
        soldItem = (SoldItem) data.getParcelable("soldItemDetail");
        TextView itemName = (TextView) findViewById(R.id.textView4);
        TextView itemPrice = (TextView) findViewById(R.id.textView5);
        TextView itemDate = (TextView) findViewById(R.id.textView6);
        itemName.setText("Product: " + soldItem.getName());
        itemPrice.setText("Price: $" + soldItem.getTotal());
        itemDate.setText(soldItem.getDateSold());
        soldItemList = this.getIntent().getExtras().getParcelableArrayList("soldItemList");
        itemList=this.getIntent().getExtras().getParcelableArrayList("itemList");
        if (savedInstanceState != null) {
            getDataFromSavedPage(savedInstanceState);
        }
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

    public void backFunction(View v) {
        Intent myIntent = new Intent(this, HistoryList.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }
}