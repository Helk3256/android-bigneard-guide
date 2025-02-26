package com.bignebranch.android.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import kotlin.math.abs
import androidx.activity.viewModels

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var questionTextView: TextView


    val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       // ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
       //     val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
       // }

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            Block_buttons(currentIndex)
        }
        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
            Block_buttons(currentIndex)
        }
        backButton.setOnClickListener{
            quizViewModel.moveToPrevios()
            updateQuestion()
            Block_buttons(currentIndex)
        }
        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        Block_buttons(quizViewModel.currentIndex)
        val correctAnswer = quizViewModel.currentQuestionAnswer

        var messageResId = 0
        if(userAnswer == correctAnswer){
            messageResId = R.string.correct_toast
            quizViewModel.correct_answers.add(quizViewModel.currentIndex)
        }
        else{
            messageResId = R.string.incorrect_toast
        }
        Toast.makeText(this , messageResId, Toast.LENGTH_SHORT).show()
        quizViewModel.blocked_questions.add(quizViewModel.currentIndex)
        Block_buttons(quizViewModel.currentIndex)
        if(quizViewModel.blocked_questions.size == quizViewModel.questionBankSize){
            Correct_percent()
        }
    }

    private fun Correct_percent() {
        var percent = ((quizViewModel.correct_answers.size.toFloat() / quizViewModel.questionBankSize) * 100.0).toInt()
        var messageResIdCorr = ("The percent of your correct answers is" + " " + percent.toString() + "%")
        Toast.makeText(this, messageResIdCorr, Toast.LENGTH_SHORT).show()
        quizViewModel.blocked_questions = mutableListOf<Int>()
        quizViewModel.correct_answers = mutableListOf<Int>()
    }

    private fun Block_buttons(question_index : Int){
        if(quizViewModel.blocked_questions.contains(question_index)){
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        else{
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }
}