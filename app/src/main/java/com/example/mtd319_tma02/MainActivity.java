package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ViewPager viewPager;
    private SliderAdapter sliderAdapter;
    private RelativeLayout relativeLayout;
    private AnimationDrawable animationDrawable;
    private Button nextBtn;
    private Button backBtn;
    private Button learnBtn;
    private ImageButton musicBtn;
    private MediaPlayer mediaPlayer;

    private int currentPage;
    boolean musicClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.layout1);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();
        DotsIndicator dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
        viewPager=findViewById(R.id.viewpager);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        nextBtn = findViewById(R.id.nextBtn);
        backBtn = findViewById(R.id.backBtn);
        learnBtn = findViewById(R.id.learnBtn);
        musicBtn = findViewById(R.id.musicBtn);
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        System.out.println("test github");
        dotsIndicator.attachTo(viewPager);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage + 1);
//                currentPage += 1;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("page",String.format("value = %d",currentPage));
                viewPager.setCurrentItem(currentPage - 1);
//                currentPage -= 1;
            }
        });

        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!musicClick) {
                    Toast.makeText(getApplicationContext(), "Play music", Toast.LENGTH_SHORT).show();
                    mediaPlayer.start();
                    musicBtn.setImageResource(R.drawable.music_click);
                    musicClick = true;
                }else{
                    Toast.makeText(getApplicationContext(), "Stop playing music", Toast.LENGTH_SHORT).show();
                    mediaPlayer.pause();
                    musicBtn.setImageResource(R.drawable.music_unclick);
                    musicClick = false;
                }

            }
        });



        ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Positon value",(String.format("value = %d", position)));

                if (position==0){
                    relativeLayout.setBackgroundResource(R.drawable.gradient_animation123);
                    animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
                    addAnimation(animationDrawable);

                    nextBtn.setEnabled(true);
                    backBtn.setEnabled(false);
                    learnBtn.setEnabled(false);
                    nextBtn.setText("Next");
                    backBtn.setText("");
                    backBtn.setVisibility(View.INVISIBLE);
                    learnBtn.setVisibility(View.INVISIBLE);
                    currentPage = position;
                }
                if (position==1){
                    relativeLayout.setBackgroundResource(R.drawable.gradient_animation456);
                    animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
                    addAnimation(animationDrawable);

                    nextBtn.setEnabled(true);
                    backBtn.setEnabled(true);
                    learnBtn.setEnabled(true);
                    nextBtn.setText("Next");
                    backBtn.setText("Back");
                    backBtn.setVisibility(View.VISIBLE);
                    learnBtn.setVisibility(View.VISIBLE);
                    currentPage = position;



                }
                if (position==2) {
                    relativeLayout.setBackgroundResource(R.drawable.gradient_animation789);
                    animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
                    addAnimation(animationDrawable);

                    nextBtn.setEnabled(true);
                    backBtn.setEnabled(true);
                    nextBtn.setText("Next");
                    backBtn.setText("Back");
                    backBtn.setVisibility(View.VISIBLE);
                    currentPage = position;
                }
                if (position==3) {
                    relativeLayout.setBackgroundResource(R.drawable.gradient_animation_101112);
                    animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
                    addAnimation(animationDrawable);

                    nextBtn.setEnabled(true);
                    backBtn.setEnabled(true);
                    nextBtn.setText("Finish");
                    backBtn.setText("Back");
                    backBtn.setVisibility(View.VISIBLE);
                    currentPage = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        };
        viewPager.addOnPageChangeListener(viewListener);
    }

    public void addAnimation(AnimationDrawable animationDrawable){
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();
    }

    public void exploreActivity(View view) {
        Intent intent = new Intent();
        Log.i("page",String.format("value = %d",currentPage));
        if (currentPage==1){
            intent = new Intent(this,ExploreActivity.class);
        }
        if (currentPage==2){
            intent = new Intent(this,HostActivity.class);
        }

        if (currentPage==3){
            intent = new Intent(this,UpdateOrderActivity.class);
        }
        startActivity(intent);

    }

    public void onPause() {
        super.onPause();
        mediaPlayer.release();
    }
}