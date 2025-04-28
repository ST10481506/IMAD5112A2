package com.example.flashcard

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcard.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val results = intent.getSerializableExtra("RESULTS") as ArrayList<Pair<Boolean, Boolean>>
        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", results.size)

        displayResults(results)
        binding.tvFinalScore.text = "$score/$total"

        binding.btnExit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun displayResults(results: List<Pair<Boolean, Boolean>>) {
        for ((index, pair) in results.withIndex()) {
            val (isCorrect, userAnswer) = pair
            val tv = TextView(this)
            tv.textSize = 16f

            if (isCorrect) {
                tv.setTextColor(resources.getColor(R.color.textSecondary, null))
                tv.text = "${index + 1}. Correct: $userAnswer"
            } else {
                tv.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                tv.text = "${index + 1}. Incorrect: $userAnswer"
                tv.setTypeface(null, android.graphics.Typeface.BOLD)
            }

            binding.reviewContainer.addView(tv)
        }
    }
}