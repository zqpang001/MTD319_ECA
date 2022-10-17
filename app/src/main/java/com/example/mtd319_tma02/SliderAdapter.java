package com.example.mtd319_tma02;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context=context;

    }

    public int[] slide_images={
            R.drawable.logo,
            R.drawable.explore,
            R.drawable.host,
            R.drawable.delivery,
    };

    public String[] slide_headings={
            "SGroupBuy",
            "Explore",
            "Host",
            "Update Delivery"
    };

    public String[] slide_descs={
            "SGroupBuy is a platform app that allow users to search for products they're interested in and host groupbuy sales for them. Customers get access to better products at much lower prices.",
            "Search in your community for the goods—from foods to electronics—that you are interested in and buy them for less.",
            "Help your community and make profit at the same time by hosting group buy.",
            "Allow the seller to update when the order is ready for pickup, and the buyer to  receive notification of order updates.",
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== (RelativeLayout) object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slider,container,false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_images);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_headings);
        TextView slideDescs = (TextView) view.findViewById(R.id.slide_descs);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescs.setText(slide_descs[position]);
        container.addView(view);
        return view;


    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
         container.removeView((RelativeLayout)object);
    }

}
