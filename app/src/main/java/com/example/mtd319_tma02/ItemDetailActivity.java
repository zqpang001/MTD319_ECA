package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Map;
import java.util.UUID;

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

    public void onClickPurchaseBtn(View view) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/purchase?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                response -> Toast.makeText(this,"Purchase Success",Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this,"Purchase Error",Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Purchase purchase = new Purchase(titleDetail.getText().toString(),priceDetail.getText().toString()
                        ,SignInActivity.usernameSession,"Pending collection ",SignInActivity.listingItemA.get(HomeItemAdapter.selected).uuid);
                Gson gson = new Gson();
                String jsonString = gson.toJson(purchase);
                Map map = gson.fromJson(jsonString, Map.class);
                Log.d("getMap: ",map.toString());
                return map;
            }
        };
        queue.add(stringRequest);
//        intent = new Intent(this,SignInActivity.class);
//        startActivity(intent);
    }
}