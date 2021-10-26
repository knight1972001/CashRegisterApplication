package com.example.cashregister;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class ManagementPanel extends AppCompatActivity {
    private ArrayList<SoldItem> soldItemList = new ArrayList<SoldItem>();
    private ArrayList<Item> itemList = new ArrayList<>();
    public ActivityResultLauncher<Intent> newLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_panel);
        getSupportActionBar().hide(); //hide actionbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide status
        soldItemList = this.getIntent().getExtras().getParcelableArrayList("soldItemList");
        itemList = this.getIntent().getExtras().getParcelableArrayList("itemList");

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

    public void goToHistoryList(View v) {
        soldItemList.forEach(item -> {
            item.getDateSold();
        });
        Intent myIntent = new Intent(this, HistoryList.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtras(bundle);

        startActivity(myIntent);
    }

    public void backFunction(View v) {
        Intent myIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }
}