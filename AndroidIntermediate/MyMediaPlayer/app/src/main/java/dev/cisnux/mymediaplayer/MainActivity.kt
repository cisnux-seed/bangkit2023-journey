package dev.cisnux.mymediaplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.mymediaplayer.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isReady = false
    private lateinit var mMediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        binding.btnPlay.setOnClickListener {
            if (!isReady) {
                mMediaPlayer.prepareAsync()
            } else {
                mMediaPlayer.start()
            }
        }
        binding.btnPause.setOnClickListener {
            if (mMediaPlayer.isPlaying) {
                mMediaPlayer.pause()
            }
        }
        binding.btnLooping.setOnClickListener {
            mMediaPlayer.isLooping = !mMediaPlayer.isLooping
        }
        binding.btnStop.setOnClickListener {
            if (mMediaPlayer.isPlaying) {
                mMediaPlayer.stop()
                isReady = false
            }
        }
    }

    private fun init() {
        mMediaPlayer = MediaPlayer()
        val attribute = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer.setAudioAttributes(attribute)
        val afd = resources.openRawResourceFd(R.raw.guitar_solo)
        try {
            mMediaPlayer.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mMediaPlayer.setOnPreparedListener {
            isReady = true
            mMediaPlayer.start()
        }
        mMediaPlayer.setOnErrorListener { _, _, _ -> false }
    }
}