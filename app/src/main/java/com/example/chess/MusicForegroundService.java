package com.example.chess;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicForegroundService extends Service {

    MediaPlayer music;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    public MusicForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        music = MediaPlayer.create(this, R.raw.back_ground_songs);
        music.setLooping(true);
        Log.d("service", "MusicForegroundService: Media player created by user.");
    }
}