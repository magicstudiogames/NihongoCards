package maou.studio.nihongocards

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import maou.studio.nihongocards.databinding.ActivityMainBinding
import android.media.MediaPlayer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NihongoCards)
        enableEdgeToEdge()

        // Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.soudtrack)
        mediaPlayer.isLooping = true

        // Buttons Config
        binding.musicButton.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                binding.musicButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.music_off_round, 0,0,0)
            } else {
                mediaPlayer.start()
                binding.musicButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.music_note_round, 0,0,0)
            }
        }

        binding.startButton.setOnClickListener {

        }

        binding.optButton.setOnClickListener {

        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
        binding.musicButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.music_note_round, 0,0,0)
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}