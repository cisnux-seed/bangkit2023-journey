package dev.cisnux.simplenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import dev.cisnux.simplenotif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // aksi untuk onClick pada button
    /**
     * Digunakan untuk memanggil fungsi langsung dari layout,
     * syaratnya yaitu fungsi tersebut memiliki parameter
     * View.
     * */
    fun sendNotification(view: View) {
        /**
         * Pending intent adalah sebuah token yang
         * diberikan untuk aplikasi lainnya (termasuk juga
         * aplikasi itu sendiri). Contohnya seperti
         * NotificationManager, AlarmManager,
         * HomeScreenAppWidgetManager, dan lain-lain.
         * */
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentIntent(pendingIntent)
            setSmallIcon(R.drawable.ic_notifications_white_48px)
            setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ic_notifications_white_48px
                )
            )
            setContentTitle(resources.getString(R.string.content_title))
            setContentText(resources.getString(R.string.subtext))
            setAutoCancel(true)
            /*
            * untuk android Oreo ke atas perlu menambahkan notification
            * channel
            * */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /*Create or update.*/
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = CHANNEL_NAME

                setChannelId(CHANNEL_ID)
                mNotificationManager.createNotificationChannel(channel)
            }
        }
        val notification = mBuilder.build()
        // notify
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }
}