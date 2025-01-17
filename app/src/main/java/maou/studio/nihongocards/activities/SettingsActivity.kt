package maou.studio.nihongocards.activities

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import maou.studio.nihongocards.R
import maou.studio.nihongocards.databinding.ActivitySettingsBinding
import maou.studio.nihongocards.objects.HiraganaAlphabet
import maou.studio.nihongocards.objects.KatakanaAlphabet

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var sharedPreferences: SharedPreferences
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NihongoCards)
        enableEdgeToEdge()

        // Binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val savedVolume = sharedPreferences.getFloat("volume", 0.5f)

        //Music
        mediaPlayer = MediaPlayer.create(this, R.raw.soudtrack)
        mediaPlayer.isLooping = true
        mediaPlayer.setVolume(savedVolume, savedVolume)
        restoreCurrentPosition()
        mediaPlayer.start()

        // Set volume progress
        val volumeProgress = (savedVolume * 100).toInt()
        binding.volumeSeekBar.progress = volumeProgress
        binding.volumeValueTextView.text =
            getString(R.string.volume_value_format, binding.volumeSeekBar.progress)

        // Update checkbox state
        updateCheckBoxesState()

        // Set listeners for checkboxes
        binding.hiraganaCheckBox.setOnCheckedChangeListener { _, isChecked ->
            HiraganaAlphabet.setPlaying(isChecked)
        }
        binding.katakanaCheckBox.setOnCheckedChangeListener { _, isChecked ->
            KatakanaAlphabet.setPlaying(isChecked)
        }

        binding.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val volume = progress / 100f
                mediaPlayer.setVolume(volume, volume)
                binding.volumeValueTextView.text = getString(R.string.volume_value_format, progress)
                sharedPreferences.edit().putFloat("volume", volume).apply()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun updateCheckBoxesState() {
        binding.hiraganaCheckBox.isChecked = HiraganaAlphabet.isPlaying()
        binding.katakanaCheckBox.isChecked = KatakanaAlphabet.isPlaying()
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        val savedVolume = sharedPreferences.getFloat("volume", 0.5f)
        mediaPlayer.setVolume(savedVolume, savedVolume)
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.release()
        }
    }

    private fun restoreCurrentPosition() {
        currentPosition = sharedPreferences.getInt("currentPosition", 0)
        mediaPlayer.seekTo(currentPosition)
    }

}
