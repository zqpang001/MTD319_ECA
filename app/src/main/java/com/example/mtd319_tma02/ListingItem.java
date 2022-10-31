package com.example.mtd319_tma02;

import android.graphics.Bitmap;

public class ListingItem {
    public String category;
    public String listingTitle;
    public String price;
    public String quantity;
    public String location;
    public String deliveryAvailability;
    public String image;
    public String username;
    public String uuid;

    public ListingItem(String category,String listingTitle, String price, String quantity
            ,String location,String deliveryAvailability,String image,String username,String uuid){
        this.category=category;
        this.listingTitle=listingTitle;
        this.price=price;
        this.quantity=quantity;
        this.location=location;
        this.deliveryAvailability=deliveryAvailability;
        this.image=image;
        this.username=username;
        this.uuid=uuid;
    }

    public String getImage() {
        return image;
    }

    public String getListingTitle() {
        return listingTitle;
    }
}
