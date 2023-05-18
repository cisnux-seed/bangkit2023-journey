package dev.cisnux.myservice

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import dev.cisnux.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var backgroundServiceIntent: Intent
    private lateinit var foregroundServiceIntent: Intent
    private lateinit var boundServiceIntent: Intent

    // it will run when user granted or not granted the permission
    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
        if (it)
            startForegroundServices()
        else
            Snackbar.make(
                binding.root, "Please grant Notification permission from App Settings",
                Snackbar.LENGTH_LONG
            ).show()

    }
    private var boundStatus = false
    private lateinit var boundService: MyBoundService
    /**
     * Kode di bawah merupakan sebuah listener untuk menerima callback dari ServiceConnetion.
     * */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBoundService.MyBinder
            boundService = myBinder.getService
            boundStatus = true
            getNumberFromService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            boundStatus = false
        }

    }

    private fun getNumberFromService() {
        boundService.numberLiveData.observe(this) { number ->
            binding.tvBoundServiceNumber.text = number.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backgroundServiceIntent = Intent(this, MyBackgroundService::class.java)
        foregroundServiceIntent = Intent(this, MyForegroundService::class.java)
        boundServiceIntent = Intent(this, MyBoundService::class.java)

        binding.btnStartBackgroundService.setOnClickListener {
            startService(backgroundServiceIntent)
        }

        /**
         * stopService dipakai untuk mematikan
         * service secara langsung dari luar kelas service,
         * sedangkan stopSelf() digunakan untuk menghentikan
         * atau mematikan service dari dalam kelas service itu sendiri.
         * */
        binding.btnStopBackgroundService.setOnClickListener {
            stopService(backgroundServiceIntent)
        }

        binding.btnStartForegroundService.setOnClickListener {
            // it will run first to check the permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                )
                    startForegroundServices()
                else
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            else
                startForegroundServices()
        }

        binding.btnStopBackgroundService.setOnClickListener {
            stopService(foregroundServiceIntent)
        }

        binding.btnStartBoundService.setOnClickListener {
            bindService(boundServiceIntent, connection, BIND_AUTO_CREATE)
        }

        binding.btnStopBoundService.setOnClickListener {
            unbindService(connection)
        }
    }

    private fun startForegroundServices() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(foregroundServiceIntent)
        } else {
            startService(foregroundServiceIntent)
        }
    }

    override fun onStop() {
        super.onStop()
        if (boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }
}