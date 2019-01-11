package com.example.android.practice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    private final String ADDRESS = "mailto:tumanov.kld@gmail.com";
    private final String SUBJECT = "Order from Music Shop";
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(R.string.your_order);

        Intent orderIntent = getIntent();
        TextView orderTextView = findViewById(R.id.order_text_view);
        text = createOrderString(orderIntent);
        orderTextView.setText(text);
    }



    private String createOrderString(Intent orderIntent) {

        String userName = orderIntent.getStringExtra("userName");
        String goodsName = orderIntent.getStringExtra("goodsName");
        int quantity = orderIntent.getIntExtra("quantity", 0);
        double price = orderIntent.getDoubleExtra("price", 0);
        double orderPrice = orderIntent.getDoubleExtra("orderPrice", 0);

        return "Customer name: " + userName
                + "\nGoods name: " + goodsName
                + "\nQuantity: " + quantity
                + "\nPrice: " + price
                + "\n\nTotal: " + orderPrice;
    }

    public void submitOrder(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(ADDRESS));
        intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
