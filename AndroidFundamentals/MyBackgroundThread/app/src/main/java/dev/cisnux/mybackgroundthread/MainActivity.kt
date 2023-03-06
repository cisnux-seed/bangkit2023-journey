package dev.cisnux.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import dev.cisnux.mybackgroundthread.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // background thread
        val executor = Executors.newSingleThreadExecutor()
        /**
         * Kemudian pada Handler di sini Anda menggunakan getMainLooper karena Anda ingin proses
         * yang di dalam Handler dijalankan di main/ui thread. Pada kasus lain, jika Anda ingin
         * Handler berjalan dengan thread yang sama dengan sebelumnya Anda bisa menggunakan myLooper.
         * */
        val handler = Handler(Looper.getMainLooper())

        with(binding) {
            btnStart.setOnClickListener {
                // run a task in background thread
                executor.execute {
                    try {
                        // simulate process compressing
                        for (i in 0..10) {
                            Thread.sleep(500)
                            val percentage = i * 10
                            handler.post {
                                // update ui in main thread
                                if (percentage == 100) {
                                    tvStatus.text = getString(R.string.task_completed)
                                } else {
                                    tvStatus.text = getString(R.string.compressing, percentage)
                                }
                            }
                        }
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}