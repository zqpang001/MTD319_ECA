package com.example.mtd319_tma02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeItemAdapter extends RecyclerView.Adapter<MyHomeView> {
    List<ListingItem> listingItems;
    Context context;
    public HomeItemAdapter(List<ListingItem> listingItems) {
        this.listingItems = listingItems;
    }


    @NonNull
    @Override
    public MyHomeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context =  parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemsView = inflater.inflate(R.layout.home_listingitem,parent,false);
        MyHomeView viewHolder= new MyHomeView(itemsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHomeView holder, int position) {
//        ListingItem listingItem =

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
