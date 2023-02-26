package dev.cisnux.volumecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.cisnux.volumecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            binding.result.text = savedInstanceState.getString(STATE_RESULT)
            binding.result.visibility = View.VISIBLE
        }

        binding.btnCalculate.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, binding.result.text.toString())
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputHeight = binding.inputHeight.text.toString().trim()
            val inputWidth = binding.inputWidth.text.toString().trim()
            val inputLength = binding.inputLength.text.toString().trim()

            var isEmpty = false

            if (inputHeight.isEmpty()) {
                isEmpty = true
                binding.inputHeight.error = "Field ini tidak boleh kosong"
            }
            if (inputLength.isEmpty()) {
                isEmpty = true
                binding.inputLength.error = "Field ini tidak boleh kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmpty = true
                binding.inputWidth.error = "Field ini tidak boleh kosong"
            }

            if (!isEmpty) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                with(binding.result) {
                    text = volume.toString()
                    visibility = View.VISIBLE
                }
            }
        }
    }
}