package dev.cisnux.myviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
//import androidx.lifecycle.ViewModelProvider
import dev.cisnux.myviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // use viewModels instead of activityViewModels when using Activity
    // use activityViewModels for sharing ViewModel in fragments
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        displayResult()

        with(binding) {
            btnCalculate.setOnClickListener {
                val width = edtWidth.text.toString()
                val height = edtHeight.text.toString()
                val length = edtLength.text.toString()

                when {
                    width.isEmpty() -> {
                        edtWidth.error = "Masih kosong"
                    }
                    height.isEmpty() -> {
                        edtHeight.error = "Masih kosong"
                    }
                    length.isEmpty() -> {
                        edtLength.error = "Masih kosong"
                    }
                    else -> {
                        viewModel.calculate(width, height, length)
                        displayResult()
                    }
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = viewModel.result.toString()
    }
}