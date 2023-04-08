package org.test.sharedpreferences

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import org.test.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val preferences: SharedPreferences by lazy {
        SharedPreferences(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bgColor = preferences.color
        val number = preferences.number

        with(binding) {
            tvNumber.background =
                AppCompatResources.getDrawable(this@MainActivity, COLORS[bgColor] ?: R.color.grey)
            tvNumber.text = number.toString()
            btnBlack.setOnClickListener(this@MainActivity)
            btnBlue.setOnClickListener(this@MainActivity)
            btnRed.setOnClickListener(this@MainActivity)
            btnGreen.setOnClickListener(this@MainActivity)
            btnCount.setOnClickListener(this@MainActivity)
            btnReset.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBlack -> {
                binding.tvNumber.background = AppCompatResources.getDrawable(this, R.color.black)
                preferences.setColor("black")
            }
            R.id.btnRed -> {
                binding.tvNumber.background = AppCompatResources.getDrawable(this, R.color.red)
                preferences.setColor("red")
            }
            R.id.btnBlue -> {
                binding.tvNumber.background = AppCompatResources.getDrawable(this, R.color.blue)
                preferences.setColor("blue")
            }
            R.id.btnGreen -> {
                binding.tvNumber.background = AppCompatResources.getDrawable(this, R.color.green)
                preferences.setColor("green")
            }
            R.id.btnCount -> {
                var number = binding.tvNumber.text.toString().toInt()
                number++
                binding.tvNumber.text = number.toString()
                preferences.setNumber(number)
            }
            R.id.btnReset -> {
                binding.tvNumber.text = "0"
                binding.tvNumber.background = AppCompatResources.getDrawable(this, R.color.grey)
                preferences.clear()
            }
        }
    }

    companion object {
        private val COLORS = mapOf(
            "red" to R.color.red,
            "green" to R.color.green,
            "blue" to R.color.blue,
            "black" to R.color.black
        )
    }
}