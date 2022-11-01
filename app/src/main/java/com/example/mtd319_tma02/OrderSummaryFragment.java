package com.example.mtd319_tma02;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderSummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSummaryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewSummary;
    static ArrayList<Purchase> purchaseOrderSummary = new ArrayList<>();
    static ArrayList<ListingItem> listingItemOrderSummary = new ArrayList<>();
    static ArrayList<String> purchasedList = new ArrayList<>();
    static Purchase[] purchaseArrayList;
    final Handler handler = new Handler();


    public OrderSummaryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderSummaryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderSummaryFragment newInstance(String param1, String param2) {
        OrderSummaryFragment fragment = new OrderSummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        handler.postDelayed(dataInitiaize, 2000);
        getPurchaseData();
        recyclerViewSummary = view.findViewById(R.id.recyclerViewSummary);

        recyclerViewSummary.setLayoutManager(new LinearLayoutManager(getContext()));


    }

    private Runnable dataInitiaize = new Runnable() {
        @Override
        public void run() {
            String listingitemString;
            String purchaseitemString;
            purchaseOrderSummary.removeAll(purchaseOrderSummary);
            listingItemOrderSummary.removeAll(listingItemOrderSummary);
            Log.d("data initiaize", "usernameSession" + SignInActivity.usernameSession);
            Log.d("data initiaize", "check list size " + purchasedList.size());
            for (int i = 0; i < SignInActivity.listingItemArray.length; i++) {
                for (int y = 0; y < purchasedList.size(); y++) {
//                Log.d("data initiaize", "h" + SignInActivity.listingItemArray[i].username);
                    Log.d("data initiaize", "check data " + SignInActivity.listingItemArray[i].uuid + "purchasedList " + purchasedList.get(y));
                    listingitemString = SignInActivity.listingItemArray[i].uuid;
                    purchaseitemString = purchasedList.get(y);
                    if (listingitemString != null && purchaseitemString != null) {
                        if (listingitemString.equals(purchaseitemString)) {
                            listingItemOrderSummary.add(SignInActivity.listingItemArray[i]);
                        }
                    }
                }
            }
            Log.d("data initiaize", "ListItemOrderSummary" + listingItemOrderSummary.toString());
            HomeItemAdapter homeItemAdapter = new HomeItemAdapter(listingItemOrderSummary, new HomeItemAdapter.ItemClickListener() {
                @Override
                public void onItemClick(ListingItem listingItem) {
                    Log.d("fragment", "onItemClick: This is fragment Summary");
//                    Intent intent = new Intent(getContext(), SignInActivity.class);
//                    startActivity(intent);
                }
            });
            recyclerViewSummary.setAdapter(homeItemAdapter);
        }
    };

    public void getPurchaseData() {
        // for listItem Array
//        ArrayList<String> purchasedList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://mtd319-ed05.restdb.io/rest/purchase?apikey=6357f014626b9c747864aeeb";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                purchaseOrderSummary.removeAll(purchaseOrderSummary);
                Log.d("response: ", response);
                Gson gson = new Gson();
                purchaseArrayList = gson.fromJson(response, Purchase[].class);
                for (int i = 0; i < purchaseArrayList.length; i++) {
                    Log.d("Purchase items: ", " purchase title " + purchaseArrayList[i].buyer);
                    purchasedList.add(purchaseArrayList[i].uuid);
                }
                Log.d("purchasedList ", "" + purchasedList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

}