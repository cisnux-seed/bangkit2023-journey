package dev.cisnux.myservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyBoundService : Service() {
    private var binder = MyBinder()
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)
    val numberLiveData = MutableLiveData<Int>()

    /**
     * Kali ini, kita tidak menerapkan metode onStartCommand,
     * melainkan metode onBinding. Hal ini karena
     * bound service bersifat mengikat pada sebuah object,
     * contohnya Activity.
     * */
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ")
        serviceScope.launch {
            for(i in 1..50){
                delay(1000)
                Log.d(TAG, "Do something $i")
                numberLiveData.postValue(i)
            }
            Log.d(TAG, "Service dihentikan")
        }
        return binder
    }

    /**
     * Fungsinya untuk mengikat kelas service. Kelas MyBinder yang diberi turunan kelas Binder mempunyai fungsi untuk melakukan mekanisme pemanggilan prosedur jarak jauh.
     * */
    internal inner class MyBinder : Binder() {
        val getService: MyBoundService = this@MyBoundService
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        serviceJob.cancel()
        return super.onUnbind(intent)
    }

    /**
     * Additionally, if your service is started and accepts binding, then when the system calls your onUnbind() method, you can optionally return true if you want to receive a call to onRebind() the next time a client binds to the service. onRebind() returns void, but the client still receives the IBinder in its onServiceConnected() callback. The following figure illustrates the logic for this kind of lifecycle.
     * */
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind: ")
    }

    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }
}