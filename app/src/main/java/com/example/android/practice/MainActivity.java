package com.example.android.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.practice.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private int quantity = 0;
    private Spinner spinner;
    private ArrayList<String> spinnerArrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private HashMap<String, Integer> goodsMap = new HashMap<>();

    private String goodsName;
    private double price;

    private EditText nameEditText;

    private final String GUITAR = "Guitar";
    private final String DRUMS = "Drums";
    private final String KEYBOARD = "Keyboard";

    {
        spinnerArrayList.add(GUITAR);
        spinnerArrayList.add(DRUMS);
        spinnerArrayList.add(KEYBOARD);

        goodsMap.put(GUITAR, 500);
        goodsMap.put(DRUMS, 1500);
        goodsMap.put(KEYBOARD, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditText = findViewById(R.id.nameEditText);
        displayQuantity();
        displayPrice();
        createSpinner();
    }

    private void createSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void decrement(View view) {
        if(quantity > 0) {
            quantity--;
        }
        displayQuantity();
        displayPrice();
    }

    public void increment(View view) {
        if(quantity < 10) {
            quantity++;
        }
        displayQuantity();
        displayPrice();
    }

    private void displayQuantity() {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }
    private void displayPrice() {
        TextView priceTextView = findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(price * quantity));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        goodsName = spinner.getSelectedItem().toString();
        price = goodsMap.get(goodsName);
        displayPrice();
        ImageView goodsImageView = findViewById(R.id.goods_image_view);

        switch (goodsName) {

            case GUITAR:
                goodsImageView.setImageResource(R.drawable.guitar);
                break;
            case DRUMS:
                goodsImageView.setImageResource(R.drawable.drums);
                break;
            case KEYBOARD:
                goodsImageView.setImageResource(R.drawable.keyboard);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addToCart(View view) {

        Order order = new Order();

        String userName = nameEditText.getText().toString();

        order.setUserName(userName.isEmpty() ? "no name" : userName);

        order.setGoodsName(goodsName);

        order.setQuantity(quantity);

        order.setOrderPrice(price * quantity);

        Intent orderIntent = new Intent(MainActivity.this, OrderActivity.class);
        orderIntent.putExtra("userName", order.getUserName());
        orderIntent.putExtra("goodsName", order.getGoodsName());
        orderIntent.putExtra("quantity", order.getQuantity());
        orderIntent.putExtra("price", quantity == 0 ? 0 : price);
        orderIntent.putExtra("orderPrice", order.getOrderPrice());

        startActivity(orderIntent);
    }
}
