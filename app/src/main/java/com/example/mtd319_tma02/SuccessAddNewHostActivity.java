package com.example.mtd319_tma02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SuccessAddNewHostActivity extends AppCompatActivity {

    TextView successText;
    TextView paragraphText;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_add_new_host);
        getSupportActionBar().setTitle("");

        mediaPlayer = MediaPlayer.create(this, R.raw.successaudio);
        mediaPlayer.start();
        successText = findViewById(R.id.successText);
        paragraphText = findViewById(R.id.paragraphText);
        if (ItemDetailActivity.isPurchased){
            paragraphText.setText("We appreciate you supporting the SGroupBuy. Check your shopping bag frequently for updates regarding your order.");
        }else{
            paragraphText.setText("We appreciate you supporting the neighborhood and hosting groupbuy. Check your shopping bag frequently for updates regarding your hosting order.");
        }

    }

    public void backToHome(View view) {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}