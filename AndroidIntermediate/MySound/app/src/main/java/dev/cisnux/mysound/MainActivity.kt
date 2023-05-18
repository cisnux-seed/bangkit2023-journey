package dev.cisnux.mysound

import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.mysound.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SoundPool
    private var soundId = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        // using listener to get load status
        sp.setOnLoadCompleteListener { _, _, status ->
            if (status == 0) {
                spLoaded = true
            } else {
                Toast.makeText(this@MainActivity, "Gagal load", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // after that load audio files
        soundId = sp.load(this, R.raw.clinking_glasses, 1)

        binding.btnSoundPool.setOnClickListener {
            if (spLoaded) {
                Log.d(MainActivity::class.java.simpleName, "audio has been played")
                sp.play(soundId, 1f, 1f, 0, 0, 1f)
            }
        }
    }
}