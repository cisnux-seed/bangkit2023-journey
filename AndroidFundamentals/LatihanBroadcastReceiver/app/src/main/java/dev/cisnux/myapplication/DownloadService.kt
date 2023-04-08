@file:Suppress("DEPRECATION")

package dev.cisnux.myapplication

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class DownloadService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "Download Service dijalankan")
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            // this works will be called repeatedly
            val notifyFinishIntent = Intent(MainActivity.ACTION_DOWNLOAD_STATUS)
            sendBroadcast(notifyFinishIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            enqueueWork(this, DownloadService::class.java, 101, intent)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        val TAG: String = DownloadService::class.java.simpleName
    }
}