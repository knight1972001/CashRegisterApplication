package com.example.cashregister;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<SoldItem> soldItemList = new ArrayList<>();
    private ArrayList<Item> itemList = new ArrayList<>();

    private boolean isReset = true;
    private Utils utils = new Utils();
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //hide actionbar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide status
        setContentView(R.layout.activity_main);

        if (this.getIntent().getExtras() != null) {
            soldItemList = this.getIntent().getExtras().getParcelableArrayList("soldItemList");
            if (this.getIntent().getExtras().getParcelableArrayList("itemList") != null) {
                itemList = this.getIntent().getExtras().getParcelableArrayList("itemList");
            }
        } else {
            itemList = utils.createItemData();
        }
        if (savedInstanceState != null) {
            getDataFromSavedPage(savedInstanceState);
        }

        fetchData();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        TextView productName = (TextView) findViewById(R.id.productType);
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);

        outState.putString("productName", productName.getText().toString());
        outState.putString("quantity", quantity.getText().toString());
        outState.putString("totalPrice", totalPrice.getText().toString());

        outState.putParcelableArrayList("itemList", itemList);
        outState.putParcelableArrayList("soldItemList", soldItemList);

        super.onSaveInstanceState(outState);
    }

    public void fetchData() {
        ListView mListView = (ListView) findViewById(R.id.theList);
        ItemListAdapter adapter = new ItemListAdapter(this, R.layout.adapter_view_layout, itemList);
        mListView.setAdapter(adapter);
    }

    public void getDataFromSavedPage(Bundle savedInstanceState) {
        TextView productName = (TextView) findViewById(R.id.productType);
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        productName.setText(savedInstanceState.getString("productName"));
        quantity.setText(savedInstanceState.getString("quantity"));
        totalPrice.setText(savedInstanceState.getString("totalPrice"));

        soldItemList = savedInstanceState.getParcelableArrayList("soldItemList");
        itemList = savedInstanceState.getParcelableArrayList("itemList");
    }

    public void onCLick(View v) {
        if (isReset) {
            clearTextView();
            isReset = false;
        }
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        TextView resultView = (TextView) findViewById(R.id.quantity);
        resultView.append(buttonText);
        calculatePrice();
    }

    public void renderingProductType(View v) {
        TextView itemName = (TextView) v.findViewById(R.id.textView1);
        String buttonText = itemName.getText().toString();
        TextView resultView = (TextView) findViewById(R.id.productType);
        resultView.setText(buttonText);
        calculatePrice();
    }

    public void goToManagementPanel(View v) {
        Intent myIntent = new Intent(this, ManagementPanel.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("itemList", itemList);
        bundle.putParcelableArrayList("soldItemList", soldItemList);
        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    public void deleteButton(View v) {
        TextView resultView = (TextView) findViewById(R.id.quantity);

        String str = resultView.getText().toString();
        if (str.equals("Quantity")) {
            clearTextView();
            resultView.setText("0");
        } else {
            if (str.length() > 0 && !str.equals("0")) {
                resultView.setText(utils.popCharacter(str));
                if (str.length() == 1) {
                    resultView.setText("0");
                    isReset = true;
                }
            }
        }
        calculatePrice();
    }


    public void clearTextView() {
        TextView resultView = (TextView) findViewById(R.id.quantity);
        resultView.setText("");
    }

    public void deleteEverything(View v) {
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("Quantity");
        TextView productType = (TextView) findViewById(R.id.productType);
        productType.setText("Product Type");
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        totalPrice.setText("Total");
        isReset = true;
    }

    public void buyButton(View v) {
        boolean flagSnackBar = true;
        TextView quantity = (TextView) findViewById(R.id.quantity);
        TextView productType = (TextView) findViewById(R.id.productType);
        TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
        Item product = findItem(productType.getText().toString());
        String snackbarText = "";

        if (quantity.getText().toString().equals("Quantity") || productType.getText().toString().equals("productType") || totalPrice.getText().toString().equals("Total") || quantity.getText().toString().equals("0") || product == null) {
            snackbarText = "All field are required!!!";
            flagSnackBar = false;
        } else {
            int number = Integer.parseInt(quantity.getText().toString());
            if (Integer.parseInt(quantity.getText().toString()) > product.getQuantity()) {
                snackbarText = "No Enough quantity in the stock!!!";
                flagSnackBar = false;
            }
        }

        if (!flagSnackBar) {
            View contextView = (View) findViewById(R.id.snackBar);
            contextView.getLayoutParams().height = 10;

            Snackbar mSnackbar = Snackbar.make(contextView, snackbarText, Snackbar.LENGTH_LONG);
            View mView = mSnackbar.getView();

            TextView mTextView = (TextView) mView.findViewById(R.id.snackbar_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackbar.setBackgroundTint(getColor(R.color.backgroundSnackBar)).setTextColor(getColor(R.color.black));

            mSnackbar.show();
        } else {
            product.sellAmount(Integer.parseInt(quantity.getText().toString()));
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Thank You for your purchase").setMessage("Your purchase is " + quantity.getText().toString() + " " + productType.getText().toString() + " for " + totalPrice.getText().toString());
            builder.show();
            LocalDateTime localDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = localDateTime.format(formatter);
            SoldItem soldItem = new SoldItem(productType.getText().toString(), parseInt(quantity.getText().toString()), parseDouble(totalPrice.getText().toString()), formatDateTime);
            soldItemList.add(soldItem);
            fetchData();
            deleteEverything(v);
        }
    }

    public Item findItem(String name) {
        for (Item item : itemList) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public void calculatePrice() {
        TextView productName = (TextView) findViewById(R.id.productType);
        Item product = findItem(productName.getText().toString());

        TextView quantity = (TextView) findViewById(R.id.quantity);
        if (product != null && isNumberic(quantity.getText().toString())) {
            TextView totalPrice = (TextView) findViewById(R.id.totalPrice);
            double price = Integer.parseInt(quantity.getText().toString()) * product.getUnitPrice();
            String priceString = String.format("%.2f", price);
            totalPrice.setText(priceString);
        }
    }

    public boolean isNumberic(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}