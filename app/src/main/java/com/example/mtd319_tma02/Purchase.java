package com.example.mtd319_tma02;

public class Purchase {
    public String title;
    public String price;
    public String buyer;
    public String status;
    public String uuid;


    public Purchase(String title, String price, String buyer, String status,String uuid) {
        this.title = title;
        this.price = price;
        this.buyer = buyer;
        this.status = status;
        this.uuid = uuid;
    }
}
