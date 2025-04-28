package com.example.flashcard

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding

    private val questions = listOf(
        Question("Nelson Mandela was the president in 1994",answer = true),
        Question("Gold is the heaviest metal.", answer = false),
        Question("World War II ended in 1945",answer = true),
        Question("The Great Wall of China is visible from space.", answer = false),
        Question("Africa is a country", answer = false)
    )
    private val results = ArrayList<Pair<Boolean, Boolean>>()
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showQuestion()

        binding.btnNext.setOnClickListener {
            val selectedId = binding.radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedAnswer = when (selectedId) {
                binding.radioTrue.id -> true
                else -> false
            }

            val correctAnswer = questions[currentQuestionIndex].answer
            if (selectedAnswer == correctAnswer) {
                score++
                binding.tvFeedback.text = getString(R.string.feedback_correct)
                results.add(Pair(true, selectedAnswer))
            } else {
                binding.tvFeedback.text = getString(R.string.feedback_incorrect)
                results.add(Pair(false, selectedAnswer))
            }

            // Move to next question after short delay
            binding.btnNext.postDelayed({
                currentQuestionIndex++
                if (currentQuestionIndex < questions.size) {
                    showQuestion()
                    binding.tvFeedback.text = ""
                } else {
                    goToScore()
                }
            }, 1000)
        }
    }

    private fun showQuestion() {
        val question = questions[currentQuestionIndex]
        binding.tvQuestion.text = question.text
        binding.tvQuestionCount.text = "Question ${currentQuestionIndex + 1}/${questions.size}"
        binding.radioGroup.clearCheck()
    }

    private fun goToScore() {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra("RESULTS", results)
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL", questions.size)
        startActivity(intent)
        finish()
    }
}
