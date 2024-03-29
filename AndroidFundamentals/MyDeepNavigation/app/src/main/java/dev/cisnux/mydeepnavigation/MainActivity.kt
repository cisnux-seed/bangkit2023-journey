package dev.cisnux.mydeepnavigation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import dev.cisnux.mydeepnavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenDetail.setOnClickListener(this)
        if (savedInstanceState == null) {
            showNotification(
                this,
                getString(R.string.notification_title),
                getString(R.string.notification_message),
                110
            )
        }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_open_detail) {
            val detailIntent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_TITLE, getString(R.string.detail_title))
                putExtra(DetailActivity.EXTRA_MESSAGE, getString(R.string.detail_message))
            }
            startActivity(detailIntent)
        }
    }

    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        val notifDetailIntent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_TITLE, title)
            putExtra(DetailActivity.EXTRA_MESSAGE, message)
        }

        val pendingIntent = TaskStackBuilder.create(this).run {
            addParentStack(DetailActivity::class.java)
            addNextIntent(notifDetailIntent)
            getPendingIntent(
                110,
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                else PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(title)
            setSmallIcon(R.drawable.ic_email_black_24dp)
            setContentText(message)
            color = ContextCompat.getColor(context, R.color.black)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            setSound(alarmSound)
            setContentIntent(pendingIntent)
            setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                setChannelId(CHANNEL_ID)
                notificationManagerCompat.createNotificationChannel(channel)
            }
        }
        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    companion object {
        const val CHANNEL_ID = "channel_1"
        const val CHANNEL_NAME = "Navigation channel"
    }
}