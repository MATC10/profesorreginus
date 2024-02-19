package com.matc.profesorreginus

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.musica)
        mediaPlayer.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "PLAY") {
            val songResourceId = intent.getIntExtra("SONG_RESOURCE_ID", R.raw.musica)
            mediaPlayer = MediaPlayer.create(this, songResourceId)
            mediaPlayer.isLooping = true
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            }
        } else if (intent?.action == "PAUSE") {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            }
        }
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}