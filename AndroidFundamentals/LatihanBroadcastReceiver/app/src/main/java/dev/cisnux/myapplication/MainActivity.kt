package dev.cisnux.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var requestPermissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted ->
        Toast.makeText(
            this, if (isGranted) {
                "Sms receiver permission diterima"
            } else {
                "Sms receiver permission ditolak"
            }, Toast.LENGTH_SHORT
        )
            .show()
    }
    private lateinit var downloadReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermission.setOnClickListener(this)
        binding.btnDownload.setOnClickListener(this)

        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast
                    .makeText(context, "Download Selesai", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_permission ->
                requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }
}