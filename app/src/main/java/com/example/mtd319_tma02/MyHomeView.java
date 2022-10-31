package com.example.mtd319_tma02;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHomeView extends RecyclerView.ViewHolder {

    public TextView titleItem;
    public TextView priceItem;
    public TextView remainingCount;
    public ImageView imageItem;
    public TextView usernameSession;


    public MyHomeView(@NonNull View itemView) {
        super(itemView);

        titleItem = itemView.findViewById(R.id.titleItem);
        priceItem = itemView.findViewById(R.id.priceItem);
        remainingCount = itemView.findViewById(R.id.remainingCount);
        imageItem = itemView.findViewById(R.id.imageItem);
        usernameSession = itemView.findViewById(R.id.usernameText);

    }
}
