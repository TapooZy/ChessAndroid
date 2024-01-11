package com.example.chess;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicBackgroundService extends Service {
    MediaPlayer music;
    public MusicBackgroundService() {
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
        Log.d("service", "MusicBackgroundService: Media player created by user.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        music.start();
        Log.d("service", "MusicBackgroundService: Music Service started by user.");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        music.stop();
        Log.d("service", "MusicBackgroundService: Music Service destroyed by user.");
    }
}