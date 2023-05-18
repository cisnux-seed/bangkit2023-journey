package dev.cisnux.mymediaplayer

interface MediaPlayerCallback {
    fun onPlay()
    fun onPause()
    fun onLoop()
    fun onStop()
}

