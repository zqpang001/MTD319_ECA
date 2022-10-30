package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemDetailActivity extends AppCompatActivity {

    TextView titleDetail;
    TextView priceDetail;
    TextView usernameDetail2;
    TextView locationDetail2;
    TextView deliveryMethodText2;
    ImageView itemImageDetail;
    TextView quantityCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        getSupportActionBar().setTitle("Item Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        titleDetail = findViewById(R.id.titleDetail);
        priceDetail = findViewById(R.id.priceDetail);
        usernameDetail2 = findViewById(R.id.usernameDetail2);
        locationDetail2 = findViewById(R.id.locationDetail2);
        deliveryMethodText2 = findViewById(R.id.deliveryMethodText2);
        itemImageDetail = findViewById(R.id.itemImageDetail);
        quantityCount = findViewById(R.id.quantityCount);


        titleDetail.setText(SignInActivity.listingItemA.get(HomeItemAdapter.selected).listingTitle);
        priceDetail.setText("S$"+SignInActivity.listingItemA.get(HomeItemAdapter.selected).price);
        usernameDetail2.setText(SignInActivity.listingItemA.get(HomeItemAdapter.selected).username);
        locationDetail2.setText(SignInActivity.listingItemA.get(HomeItemAdapter.selected).location);
        deliveryMethodText2.setText(SignInActivity.listingItemA.get(HomeItemAdapter.selected).location);
        quantityCount.setText(SignInActivity.listingItemA.get(HomeItemAdapter.selected).quantity);
        Glide.with(this).load(SignInActivity.listingItemA.get(HomeItemAdapter.selected).getImage()).into(itemImageDetail );

        Log.d("Listing Item","in ItemDetailActivity: "+ HomeItemAdapter.selected);
    }

}