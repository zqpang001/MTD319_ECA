package com.example.mtd319_tma02;

import android.graphics.Bitmap;

public class ListingItem {
    public String category;
    public String listingTitle;
    public String price;
    public String quantity;
    public String location;
    public String deliveryAvailability;
    public Bitmap image;

    public ListingItem(String category, String listingTitle, String price, String quantity, String location, String deliveryAvailability, Bitmap image){
        this.category=category;
        this.listingTitle=listingTitle;
        this.price=price;
        this.quantity=quantity;
        this.location=location;
        this.deliveryAvailability=deliveryAvailability;
        this.image=image;
    }
}
