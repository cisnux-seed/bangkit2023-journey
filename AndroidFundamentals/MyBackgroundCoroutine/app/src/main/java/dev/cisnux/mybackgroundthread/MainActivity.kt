package dev.cisnux.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import dev.cisnux.mybackgroundthread.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnStart.setOnClickListener {
                // background thread
                // run a task in background thread
                lifecycleScope.launch(Dispatchers.Default) {
                    // simulate process compressing
                    for (i in 0..10) {
                        /**
                         * delay adalah method khusus yang berasal dari library coroutine.
                         * Secara sekilas memiliki fungsi yang hampir mirip dengan Thread.Sleep,
                         * namun secara fundamental mereka sangatlah berbeda. Jika kita menggunakan
                         * Thread.Sleep yaitu dia akan mem-block proses secara keseluruhan.
                         * Sedangkan jika kita menggunakan delay, yang berhenti hanya yang
                         * di dalam coroutine itu saja, sedangkann coroutine lain
                         * masih bisa dijalankan.
                         * */
                        delay(500)
                        val percentage = i * 10
                        withContext(Dispatchers.Main) {
                            // update ui in main thread
                            if (percentage == 100) {
                                tvStatus.text = getString(R.string.task_completed)
                            } else {
                                tvStatus.text = getString(R.string.compressing, percentage)
                            }
                        }
                    }
                }
            }
        }
    }
}