package dev.cisnux.ticketingapps

import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.cisnux.ticketingapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        with(binding) {
            button.setOnClickListener {
                seatView.seat?.let {
                    Toast.makeText(
                        this@MainActivity,
                        "Kursi Anda nomor ${it.name}.",
                        Toast.LENGTH_SHORT
                    ).show()
                } ?: run {
                    Toast.makeText(
                        this@MainActivity,
                        "Silakan pilih kursi terlebih dahulu.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}