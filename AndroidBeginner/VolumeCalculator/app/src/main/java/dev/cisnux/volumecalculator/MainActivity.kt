package dev.cisnux.volumecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtHeight: EditText
    private lateinit var edtWidth: EditText
    private lateinit var edtLength: EditText
    private lateinit var result: TextView
    private lateinit var btnCalculate: Button

    companion object {
        private const val STATE_RESULT = "state_result"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtHeight = findViewById(R.id.input_height)
        edtLength = findViewById(R.id.input_length)
        edtWidth = findViewById(R.id.input_width)
        result = findViewById(R.id.result)
        btnCalculate = findViewById(R.id.btn_calculate)
        savedInstanceState?.let {
            result.text = savedInstanceState.getString(STATE_RESULT)
            result.visibility = View.VISIBLE
        }

        btnCalculate.setOnClickListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, result.text.toString())
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_calculate) {
            val inputHeight = edtHeight.text.toString().trim()
            val inputWidth = edtWidth.text.toString().trim()
            val inputLength = edtLength.text.toString().trim()

            var isEmpty = false

            if (inputHeight.isEmpty()) {
                isEmpty = true
                edtHeight.error = "Field ini tidak boleh kosong"
            }
            if (inputLength.isEmpty()) {
                isEmpty = true
                edtLength.error = "Field ini tidak boleh kosong"
            }
            if (inputWidth.isEmpty()) {
                isEmpty = true
                edtWidth.error = "Field ini tidak boleh kosong"
            }

            if (!isEmpty) {
                val volume = inputLength.toDouble() * inputWidth.toDouble() * inputHeight.toDouble()
                result.run {
                    text = volume.toString()
                    visibility = View.VISIBLE
                }
            }
        }
    }
}