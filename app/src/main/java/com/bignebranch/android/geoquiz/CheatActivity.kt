package com.bignebranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bignebranch.android.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE =
    "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheatBinding.inflate(layoutInflater)
         setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val aswerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(aswerText)
            setAnswerShownResult(true)
        }

        }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }


    companion object{
            fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent{
                return Intent(packageContext, CheatActivity::class.java).apply {
                    putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                }
            }
        }
    }