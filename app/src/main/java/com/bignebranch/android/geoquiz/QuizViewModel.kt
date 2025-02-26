package com.bignebranch.android.geoquiz

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel // Добавляем import ViewModel
import kotlin.math.abs

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() { // Наследуем QuizViewModel от ViewModel
   private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))
    var currentIndex = 0

    var questionBankSize = questionBank.size

    var blocked_questions = mutableListOf<Int>()

    var correct_answers = mutableListOf<Int>()

    fun moveToPrevios{
        if((currentIndex - 1) < 0) {
            currentIndex = questionBank.size - abs(currentIndex - 1)
        }
        else {
            currentIndex = currentIndex - 1
        }
    }

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}

