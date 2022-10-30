package com.example.mtd319_tma02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Handler;

public class HomeItemAdapter extends RecyclerView.Adapter<MyHomeView> {

    List<ListingItem> listingItems;
    Context context;
    private ItemClickListener mItemListener;
    public static int selected;

    public HomeItemAdapter(List<ListingItem> listingItems, ItemClickListener itemClickListener) {
        this.listingItems = listingItems;
        this.mItemListener= itemClickListener;
    }

    public void setFilteredList(List<ListingItem> filteredList){
        this.listingItems = filteredList;
        notifyDataSetChanged();
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
        ListingItem listingItem = listingItems.get(position);
        TextView titleItem = holder.titleItem;
        titleItem.setText(listingItem.listingTitle);
        TextView priceItem = holder.priceItem;
        priceItem.setText("S$"+listingItem.price);
        TextView remainingCount = holder.remainingCount;
        remainingCount.setText(listingItem.quantity);
        TextView usernameText = holder.usernameSession;
        usernameText.setText(listingItem.username);
        ImageView imageItem = holder.imageItem;
        Log.d("piccaso why you dont wan come out","123"   +listingItem.image);
//        Picasso.get().load(listingItem.getImage()).into(imageItem);
        Glide.with(context).load(listingItem.getImage()).into(imageItem);

       holder.itemView.setOnClickListener(view -> {
           mItemListener.onItemClick(listingItems.get(position));
           selected = position;
       });

    }

    public interface ItemClickListener{
        void onItemClick(ListingItem listingItem);
    }

    @Override
    public int getItemCount() {
        Log.d("itemcount", "getItemCount: "+listingItems.size());
        return listingItems.size();
    }
}
