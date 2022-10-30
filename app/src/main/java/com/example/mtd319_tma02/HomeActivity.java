package com.example.mtd319_tma02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.cloudinary.android.MediaManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
//    static ArrayList<ListingItem> listingItemA = new ArrayList<ListingItem>();
//    static ListingItem[] listingItemArray;


    RecyclerView homeRecyclerView;
    HomeItemAdapter homeItemAdapter;
    SearchView searchView;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        HomeActivity.context = getApplicationContext();
        homeRecyclerView = findViewById(R.id.homeRecycleView);

        searchView = findViewById(R.id.searchViewHome);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }
        });

        homeItemAdapter = new HomeItemAdapter(SignInActivity.listingItemA, new HomeItemAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ListingItem listingItem) {
                showToast(listingItem.getListingTitle()+" Clicked!");
                Intent intent = new Intent(context,ItemDetailActivity.class);
                startActivity(intent);
            }
        });
        homeRecyclerView.setAdapter(homeItemAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));



//        SignInActivity.callListingItem();
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url = "https://mtd319-ed05.restdb.io/rest/host?apikey=6357f014626b9c747864aeeb";
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("response: ",response);
//                Gson gson = new Gson();
//                listingItemArray = gson.fromJson(response,ListingItem[].class);
//                for (int i = 0; i < listingItemArray.length; i++){
//                    Log.d("listing items: ", "  "+listingItemArray[i].image);
//                    listingItemA.add(listingItemArray[i]);
//
//                }
////                Log.d("Home Activity get from restdb: " , Arrays.toString(listingItemA));
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        queue.add(stringRequest);




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
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                ,ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }

    private void filterList(String text) {
        List<ListingItem> filteredList = new ArrayList<>();
        for (ListingItem item : SignInActivity.listingItemA) {
            if (item.getListingTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this,"No data found", Toast.LENGTH_SHORT).show();
        }else{
            homeItemAdapter.setFilteredList(filteredList);
        }
    }

    private void showToast(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}