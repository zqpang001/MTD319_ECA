package com.example.mtd319_tma02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.cloudinary.android.MediaManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    static ArrayList<ListingItem> listingItemArray = new ArrayList<ListingItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://mtd319-ed05.restdb.io/rest/host?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ",response);
                Gson gson = new Gson();
                ListingItem[] listingItemA = gson.fromJson(response,ListingItem[].class);
//                for (int i = 0; i < listingItemA.length; i++){
//                    Log.d("suss","username: " + listingItemA[i].listingTitle);
//                    listingItemArray.array(listingItemA[i].listingTitle,listingItemA[i].price,listingItemA[i].image);
//                }
//                Log.d("credential list " , listingItem.toString());
                Log.d("Home Activity get from restdb: " , listingItemA.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);




        BottomNavigationView bottomNavigationView= findViewById(R.id.bottomNavigationView);
        //Set Host Selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        //Bottom navigation selected
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.host:
                        startActivity(new Intent(getApplicationContext()
                                ,AddNewHostActivity_Task.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.bag:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AddNewHostActivity_Task.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext()
//                                ,AddNewHostActivity_Task.class));
//                        overridePendingTransition(0,0);
//                        return true;

                }
                return false;
            }
        });
    }



}