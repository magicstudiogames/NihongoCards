package maou.studio.nihongocards

import android.content.Context
import android.media.MediaPlayer

object MusicPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = true
    private var currentVolume = 0.5f

    fun initialize(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.soudtrack)
        mediaPlayer?.isLooping = true
    }

    fun start() {
        mediaPlayer?.start()
        isPlaying = true
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }

    fun pause() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    fun setVolume(volume: Float) {
        mediaPlayer?.setVolume(volume, volume)
        currentVolume = volume
    }

    fun getCurrentVolume(): Float {
        return currentVolume
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

}