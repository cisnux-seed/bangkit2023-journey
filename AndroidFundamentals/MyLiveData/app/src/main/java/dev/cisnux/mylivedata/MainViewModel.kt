package dev.cisnux.mylivedata

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Timer
import java.util.TimerTask

class MainViewModel : ViewModel() {
    private val mInitialTime = SystemClock.elapsedRealtime()
    private val _mElapsedTime = MutableLiveData<Long?>()
    val mElapsedTime: LiveData<Long?> = _mElapsedTime

    companion object {
        private const val ONE_SECOND = 1000
    }

    init {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                _mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }
}