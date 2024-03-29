package dev.cisnux.mysettingpreference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.cisnux.mysettingpreference.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.settingHolder, MyPreferenceFragment())
            .commit()
    }
}