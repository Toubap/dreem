package com.baptisterssl.dreemtest.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log

class PlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {

    companion object {
        private const val ACTION_PLAY = "action.PLAY"
        private const val ACTION_PAUSE = "action.PAUSE"
        private const val ACTION_STOP = "action.STOP"
        private const val KEY_MUSIC_URL = "KEY_MUSIC_URL"

        fun play(context: Context, url: String) {
            val intent = Intent(context, PlayerService::class.java).apply {
                action = ACTION_PLAY
                putExtra(KEY_MUSIC_URL, url)
            }
            context.startService(intent)
        }

        fun pause(context: Context) {
            val intent = Intent(context, PlayerService::class.java).apply {
                action = ACTION_PAUSE
            }
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, PlayerService::class.java).apply {
                action = ACTION_STOP
            }
            context.startService(intent)
        }
    }

    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_PLAY -> {
                stopAndReset()
                initMediaPlayer()
                intent.getStringExtra(KEY_MUSIC_URL)?.let { play(it) }
            }
            ACTION_PAUSE -> {
                mediaPlayer?.pause()
            }
            ACTION_STOP -> stopSelf()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAndReset()
        mediaPlayer?.release()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.e("MediaPlayer Error", "$what $extra")
        return true
    }

    /** Private methods */

    private fun initMediaPlayer() {
        if (mediaPlayer != null) return
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener(this@PlayerService)
            setOnErrorListener(this@PlayerService)
        }
    }

    private fun play(url: String) {
        mediaPlayer?.apply {
            setDataSource(url)
            prepareAsync()
        }
    }

    private fun stopAndReset() {
        mediaPlayer?.run {
            stop()
            reset()
        }
    }
}