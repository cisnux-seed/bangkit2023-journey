package dev.cisnux.mymediaplayer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.lang.ref.WeakReference

class MediaService : Service(), MediaPlayerCallback {
    private var isReady = false
    private lateinit var mMediaPlayer: MediaPlayer

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: ")
        return mMessenger.binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_CREATE -> init()
            ACTION_DESTROY -> stopSelf()
        }
        Log.d(TAG, "onStartCommand: ")
        return flags
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer.setAudioAttributes(attribute)
        val afd = resources.openRawResourceFd(R.raw.guitar_solo)
        try {
            mMediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mMediaPlayer.setOnPreparedListener {
            isReady = true
            mMediaPlayer.start()
        }
        mMediaPlayer.setOnErrorListener { _, _, _ -> false }
    }


    override fun onPlay() {
        if (!isReady) {
            mMediaPlayer.prepareAsync()
            showNotification()
        } else {
            mMediaPlayer.start()
        }
    }

    override fun onPause() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
            stopNotification()
        }
    }

    override fun onLoop() {
        mMediaPlayer.isLooping = !mMediaPlayer.isLooping
    }

    override fun onStop() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
            isReady = false
        }
    }

    private val mMessenger = Messenger(IncomingHandler(this))

    internal class IncomingHandler(playerCallback: MediaPlayerCallback) :
        Handler(Looper.getMainLooper()) {
        private val mediaPlayerCallbackWeakReference = WeakReference(playerCallback)

        override fun handleMessage(msg: Message) {
            mediaPlayerCallbackWeakReference.get()?.let {
                when (msg.what) {
                    PLAY -> it.onPlay()
                    PAUSE -> it.onPause()
                    LOOP -> it.onLoop()
                    STOP -> it.onStop()
                    else -> super.handleMessage(msg)
                }
            }
        }
    }

    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
        }
        val notification = NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle("TES1")
            .setContentText("TES2")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setTicker("TES3")
            .build()
        createChannel()
        startForeground(ONGOING_NOTIFICATION_ID, notification)

    }

    private fun createChannel() {
        val mNotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_DEFAULT_IMPORTANCE, "Battery",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.setShowBadge(false)
            channel.setSound(null, null)
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    private fun stopNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(false)
        }
    }

    companion object {
        const val ACTION_CREATE = "dev.cisnux.mymediaplayer.create"
        const val ACTION_DESTROY = "dev.cisnux.mymediaplayer.destroy"
        private val TAG = MediaService::class.java.simpleName
        const val PLAY = 0
        const val PAUSE = 1
        const val LOOP = 2
        const val STOP = 3
        const val CHANNEL_DEFAULT_IMPORTANCE = "channel_test"
        const val ONGOING_NOTIFICATION_ID = 1
    }
}
