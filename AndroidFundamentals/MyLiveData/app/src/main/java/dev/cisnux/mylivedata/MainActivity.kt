package dev.cisnux.mylivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dev.cisnux.mylivedata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mLiveDataTimerViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribe()
    }

    private fun subscribe() {
//        val elapsedTimeObserver = Observer<Long?> { aLong ->
//            val newText = resources.getString(R.string.seconds, aLong)
//            binding.timerTextView.text = newText
//        }
//        mLiveDataTimerViewModel.mElapsedTime.observe(this, elapsedTimeObserver)
        mLiveDataTimerViewModel.mElapsedTime.observe(this) { aLong ->
            val newText = resources.getString(R.string.seconds, aLong)
            binding.timerTextView.text = newText
        }
    }
}