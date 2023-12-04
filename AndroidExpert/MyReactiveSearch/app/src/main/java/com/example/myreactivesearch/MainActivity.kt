package com.example.myreactivesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.myreactivesearch.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val edPlace = binding.edPlace

        edPlace.doOnTextChanged { text, _, _, _ ->
            lifecycleScope.launch {
                viewModel.queryChannel.value = text.toString()
            }
        }
        viewModel.searchResult.observe(this) { placesItem ->
            val placesName = placesItem.map { it.placeName }
            val adapter = ArrayAdapter(
                this@MainActivity,
                androidx.appcompat.R.layout.select_dialog_item_material,
                placesName
            )
            adapter.notifyDataSetChanged()
            edPlace.setAdapter(adapter)
        }
    }
}