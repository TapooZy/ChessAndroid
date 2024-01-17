package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomePageActivity extends AppCompatActivity{
    Button cheese;
    ImageView login, register, music;
    int timesClicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        login = findViewById(R.id.ivLogin);
        register = findViewById(R.id.ivRegister);
        cheese = findViewById(R.id.btnCheese);
        music = findViewById(R.id.ivMusic);
        music.setOnClickListener(this::onClick);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
        );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        cheese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Chess.class);
                startActivity(intent);
            }
        });
    }
    public void onClick(View v){
        if (timesClicked % 2 == 0){
            timesClicked++;
            music.setImageResource(R.drawable.music_pause);
            Intent intent = new Intent(HomePageActivity.this, MusicBackgroundService.class);
            startService(intent);
        }
        else {
            timesClicked++;
            music.setImageResource(R.drawable.music_resume);
            Intent intent = new Intent(HomePageActivity.this, MusicBackgroundService.class);
            stopService(intent);
        }
    }

}