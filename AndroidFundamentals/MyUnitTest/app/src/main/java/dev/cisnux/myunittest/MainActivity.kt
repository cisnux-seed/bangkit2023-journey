package dev.cisnux.myunittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.cisnux.myunittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel(CuboidModel())
        with(binding) {
            btnSave.setOnClickListener(this@MainActivity)
            btnCalculateCircumference.setOnClickListener(this@MainActivity)
            btnCalculateVolume.setOnClickListener(this@MainActivity)
            btnCalculateSurfaceArea.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View) {
        val length = binding.edtLength.text.toString().trim()
        val width = binding.edtWidth.text.toString().trim()
        val height = binding.edtHeight.text.toString().trim()
        when {
            length.isEmpty() -> {
                binding.edtLength.error = "Field ini tidak boleh kosong"
            }
            width.isEmpty() -> {
                binding.edtWidth.error = "Field ini tidak boleh kosong"
            }
            height.isEmpty() -> {
                binding.edtHeight.error = "Field ini tidak boleh kosong"
            }
            else -> {
                val valueLength = length.toDouble()
                val valueWidth = width.toDouble()
                val valueHeight = height.toDouble()
                when (v.id) {
                    R.id.btn_save -> {
                        mainViewModel.save(valueLength, valueWidth, valueHeight)
                        visible()
                    }
                    R.id.btn_calculate_circumference -> {
                        binding.tvResults.text =
                            mainViewModel.circumference.toString()
                        gone()
                    }
                    R.id.btn_calculate_surface_area -> {
                        binding.tvResults.text =
                            mainViewModel.surfaceArea.toString()
                        gone()
                    }
                    R.id.btn_calculate_volume -> {
                        binding.tvResults.text = mainViewModel.volume.toString()
                        gone()
                    }
                }
            }
        }
    }

    private fun visible() {
        binding.btnCalculates.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
    }

    private fun gone() {
        binding.btnCalculates.visibility = View.GONE
        binding.btnSave.visibility = View.VISIBLE
    }
}