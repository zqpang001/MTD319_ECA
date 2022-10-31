package com.example.mtd319_tma02;

import static com.example.mtd319_tma02.SignInActivity.listingItemA;
import static com.example.mtd319_tma02.SignInActivity.usernameSession;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyListingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    static ArrayList<ListingItem> listingItemCurrentUser = new ArrayList<ListingItem>();

    public MyListingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyListingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyListingFragment newInstance(String param1, String param2) {
        MyListingFragment fragment = new MyListingFragment();
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
        return inflater.inflate(R.layout.fragment_my_listing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitiaize();
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        HomeItemAdapter homeItemAdapter = new HomeItemAdapter(listingItemCurrentUser, new HomeItemAdapter.ItemClickListener() {
            @Override
            public void onItemClick(ListingItem listingItem) {
//                showToast(listingItem.getListingTitle()+" Clicked!");
                Log.d("fragment", "onItemClick: This is listing fragment");
//                Intent intent = new Intent(getContext(),SignInActivity.class);
//                startActivity(intent);
            }
        });
        recyclerView.setAdapter(homeItemAdapter);


    }

    private void dataInitiaize() {
        listingItemCurrentUser.removeAll(listingItemCurrentUser);
        Log.d("data initiaize", "usernameSession" + SignInActivity.usernameSession);
        for (int i = 0; i < SignInActivity.listingItemArray.length; i++) {
//            Log.d("data initiaize", "h" + SignInActivity.listingItemArray[i].username);

            if (SignInActivity.listingItemArray[i].username.equals(SignInActivity.usernameSession)) {
//                Log.d("data initiaize", "inside if" + SignInActivity.listingItemArray[i].username);
                listingItemCurrentUser.add(SignInActivity.listingItemArray[i]);
            }
//            Log.d("data initiaize", "h" + listingItemCurrentUser.toString());

        }

    }
}