package maou.studio.nihongocards

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import maou.studio.nihongocards.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private lateinit var alphabet: List<String>
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_NihongoCards)
        enableEdgeToEdge()

        // Binding
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Alphabet
        alphabet = if (HiraganaAlphabet.isPlaying() && KatakanaAlphabet.isPlaying()) {
            shuffleAlphabets(
                HiraganaAlphabet.getShuffledAlphabet(), KatakanaAlphabet.getShuffledAlphabet()
            )
        } else if (HiraganaAlphabet.isPlaying()) {
            HiraganaAlphabet.getShuffledAlphabet()
        } else if (KatakanaAlphabet.isPlaying()) {
            KatakanaAlphabet.getShuffledAlphabet()
        } else {
            // Default to Hiragana if no option is selected
            HiraganaAlphabet.getShuffledAlphabet()
        }

        showNextCharacter()

        binding.cardTextView.setOnClickListener {
            if (currentIndex < alphabet.size - 1) {
                currentIndex++
                showNextCharacter()
            } else {
                showEndOfGameDialog()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun showNextCharacter() {
        binding.cardTextView.text = alphabet[currentIndex]
    }

    private fun showEndOfGameDialog() {
        AlertDialog.Builder(this).setTitle(getString(R.string.end_game))
            .setMessage(getString(R.string.retry))
            .setPositiveButton(getString(R.string.dialog_yes)) { _, _ ->
                restartGame()
            }.setNegativeButton(getString(R.string.dialog_no)) { _, _ ->
                finish()
            }.setCancelable(false).show()
    }

    private fun restartGame() {
        currentIndex = 0
        alphabet = if (HiraganaAlphabet.isPlaying() && KatakanaAlphabet.isPlaying()) {
            shuffleAlphabets(
                HiraganaAlphabet.getShuffledAlphabet(), KatakanaAlphabet.getShuffledAlphabet()
            )
        } else if (HiraganaAlphabet.isPlaying()) {
            HiraganaAlphabet.getShuffledAlphabet()
        } else if (KatakanaAlphabet.isPlaying()) {
            KatakanaAlphabet.getShuffledAlphabet()
        } else {
            // Default to Hiragana if no option is selected
            HiraganaAlphabet.getShuffledAlphabet()
        }
        showNextCharacter()
    }

    private fun shuffleAlphabets(hiragana: List<String>, katakana: List<String>): List<String> {
        val combinedList = hiragana + katakana
        return combinedList.shuffled()
    }

}