package dev.cisnux.myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyBackgroundService : Service() {
    private val serviceJob = Job()
    /**
     * `+` is an operator in Kotlin that is used to combine
     * CoroutineDispatchers. In this case, it is used to
     * combine the Dispatchers.Main dispatcher with another
     * dispatcher or a Job.
     * */
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    override fun onBind(intent: Intent): IBinder {
        throw UnsupportedOperationException("Not implemented yet")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "Service dijalankan...")
        serviceScope.launch {
            for (i in 1..50){
                delay(1000)
                Log.d(TAG, "Do something $i")
            }
            stopSelf()
            Log.d(TAG, "Service dihentikan")
        }
        /**
         * START_STICKY menandakan bila service tersebut dimatikan
         * oleh sistem Android karena kekurangan memori,
         * ia akan diciptakan kembali jika sudah ada memori
         * yang bisa digunakan. Sehingga, metode onStartCommand()
         * juga akan kembali dijalankan.
         * */
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
        Log.d(TAG, "onDestroy: Service dihentikan")
    }

    companion object {
        private val TAG = MyBackgroundService::class.java.simpleName
    }
}