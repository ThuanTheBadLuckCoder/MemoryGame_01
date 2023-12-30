package com.example.mymemorygame01

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins

class Game02Activity : AppCompatActivity() {

    private lateinit var mainLayout: ConstraintLayout
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView
    private var score = 0
    private var highScore = 0
    private val cards = mutableListOf<Button>()
    private val selectedCards = mutableSetOf<Int>()
    private var currentLevel = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game02_activity)

        mainLayout = findViewById(R.id.mainLayout)
        scoreTextView = findViewById(R.id.scoreTextView)
        highScoreTextView = findViewById(R.id.highScoreTextView)

        setupGame()
    }

    private fun setupGame() {
        // Initialize game state, shuffle cards, display them, etc.
        score = 0
        highScore = loadHighScore() // Implement this method to load the high score
        currentLevel = 1
        selectedCards.clear()
        displayCards(currentLevel + 2)
    }

    private fun displayCards(count: Int) {
        mainLayout.removeAllViews()
        // Assume you have a drawable for each card and identifiers are card_1, card_2, etc.
        val drawables = (1..count).map { resourceIdForCard(it) }
        drawables.shuffle()
        drawables.forEachIndexed { index, drawableId ->
            val card = Button(this).apply {
                setBackgroundResource(drawableId)
                id = View.generateViewId()
                val params = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8)
                layoutParams = params
                setOnClickListener { onCardSelected(index) }
            }
            cards.add(card)
            mainLayout.addView(card)
        }
    }

    private fun onCardSelected(cardIndex: Int) {
        if (selectedCards.contains(cardIndex)) {
            // Player selected a previously selected card
            endRound(false)
        } else {
            selectedCards.add(cardIndex)
            score += 500 // Plus any additional bonus calculations
            scoreTextView.text = "Score: $score"
            if (selectedCards.size == currentLevel + 2) {
                endRound(true)
            }
        }
    }

    private fun endRound(success: Boolean) {
        if (success) {
            // Calculate bonus points
            score += selectedCards.size * 100 * currentLevel
            if (currentLevel == 3) {
                // Game completed
                saveHighScore(score)
                showEndGameMessage(true)
                currentLevel = 1
            } else {
                currentLevel++
            }
            displayCards(currentLevel + 2)
        } else {
            // Round lost
            showEndGameMessage(false)
            currentLevel = 1
            displayCards(currentLevel + 2)
        }
        scoreTextView.text = "Score: $score"
    }

    private fun showEndGameMessage(won: Boolean) {
        // Show a Toast or Dialog here
    }

    private fun saveHighScore(score: Int) {
        // Implement this to save high score
    }

    private fun loadHighScore(): Int {
        // Implement this to load high score
        return 0
    }

    private fun resourceIdForCard(cardNumber: Int): Int {
        // Implement this to return the correct drawable resource ID for each card
        return R.drawable.card_placeholder // Replace with actual drawable resource
    }
}
