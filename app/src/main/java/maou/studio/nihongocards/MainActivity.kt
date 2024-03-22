package maou.studio.nihongocards

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import maou.studio.nihongocards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NihongoCards)
        enableEdgeToEdge()

        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MusicPlayer
        MusicPlayerManager.initialize(this)
        MusicPlayerManager.start()

        // Buttons Config
        binding.musicButton.setOnClickListener {
            toggleMusicPlayback()
        }

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java).apply {
                putExtra("isMusicPlaying", MusicPlayerManager.isPlaying())
            })
        }

        binding.optButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java).apply {
                putExtra("isMusicPlaying", MusicPlayerManager.isPlaying())
            })
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onResume() {
        super.onResume()
        updateMusicButtonIcon()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicPlayerManager.stop()
    }

    private fun toggleMusicPlayback() {
        if (MusicPlayerManager.isPlaying()) {
            MusicPlayerManager.pause()
        } else {
            MusicPlayerManager.start()
        }
        updateMusicButtonIcon()
    }

    private fun updateMusicButtonIcon() {
        val iconResource =
            if (MusicPlayerManager.isPlaying()) R.drawable.music_note_round else R.drawable.music_off_round
        binding.musicButton.setCompoundDrawablesWithIntrinsicBounds(iconResource, 0, 0, 0)
    }

}