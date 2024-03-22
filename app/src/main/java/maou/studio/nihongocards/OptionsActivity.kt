package maou.studio.nihongocards

import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import maou.studio.nihongocards.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NihongoCards)
        enableEdgeToEdge()

        // Binding
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set volume progress
        binding.volumeSeekBar.progress = (MusicPlayerManager.getCurrentVolume() * 100).toInt()
        binding.volumeValueTextView.text =
            getString(R.string.volume_value_format, binding.volumeSeekBar.progress)

        binding.volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val volume = progress / 100f
                MusicPlayerManager.setVolume(volume)
                binding.volumeValueTextView.text = getString(R.string.volume_value_format, progress)
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

}
