package com.example.myreactivesearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.myreactivesearch.network.ApiConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest

class MainViewModel : ViewModel() {
    val queryChannel = MutableStateFlow("")
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotBlank()
        }
        .mapLatest {
            ApiConfig.provideApiService().getCountry(it, ACCESS_TOKEN).features
        }
        .asLiveData()

    companion object {
        private const val ACCESS_TOKEN = "pk.eyJ1IjoiY2lzbnV4IiwiYSI6ImNsajdkMGIyNjBqc3IzY3F4Yzc1azZpYm4ifQ.JOY0hIRvS7KgwW8ACz-7sg"
    }
}