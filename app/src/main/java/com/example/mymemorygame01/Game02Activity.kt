package com.example.mymemorygame01

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout

class Game02Activity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView
    private lateinit var exitButton: Button
    private lateinit var restartButton: Button
    private var currentLevel = 1
    private var score = 0
    private var highScore = 0
    private var selectedIndices = mutableSetOf<Int>()
    private var currentRound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game02_activity)

        gridLayout = findViewById(R.id.gridLayout)
        scoreTextView = findViewById(R.id.scoreTextView)
        highScoreTextView = findViewById(R.id.highScoreTextView)
        exitButton = findViewById(R.id.exitButton)
        restartButton = findViewById(R.id.restartButton)

        exitButton.setOnClickListener { finish() }
        restartButton.setOnClickListener { restartGame() }

        setupGame()
    }

    private fun setupGame() {
        currentLevel = 1
        score = 0
        highScore = 0 // This should be loaded from SharedPreferences or another persistent storage
        currentRound = 0
        selectedIndices.clear()
        gridLayout.removeAllViews()
        populateGrid()
        updateScore()
    }

    private fun populateGrid() {
        val cardCount = currentLevel + 2
        gridLayout.columnCount = cardCount
        gridLayout.rowCount = 1

        for (i in 0 until cardCount) {
            val button = Button(this)
            button.id = i
            button.text = "Card $i"
            button.setBackgroundColor(Color.LTGRAY)
            button.setOnClickListener { onCardSelected(it.id) }
            gridLayout.addView(button)
        }
    }

    private fun onCardSelected(cardId: Int) {
        if (currentRound >= currentLevel) {
            endLevel(false)
            return
        }

        if (selectedIndices.contains(cardId)) {
            // Player selected the same card as in previous rounds
            endLevel(false)
        } else {
            selectedIndices.add(cardId)
            score += 500 * currentLevel
            if (currentRound < currentLevel - 1) {
                // Continue to next round
                currentRound++
            } else {
                // Completed level
                endLevel(true)
            }
        }
        updateScore()
    }

    private fun endLevel(success: Boolean) {
        if (success) {
            score += selectedIndices.size * 100 * currentLevel
            currentLevel++
            if (currentLevel > 3) {
                currentLevel = 1 // Reset level after level 3
                showEndGameMessage(true)
            }
            setupGame()
        } else {
            showEndGameMessage(false)
            restartGame()
        }
        updateScore()
    }

    private fun restartGame() {
        setupGame()
    }

    private fun updateScore() {
        scoreTextView.text = "Score: $score"
        if (score > highScore) {
            highScore = score
            highScoreTextView.text = "High Score: $highScore"
            // Save the new high score to SharedPreferences or another persistent storage
        }
    }

    private fun showEndGameMessage(won: Boolean) {
        // Show a message to the player. Use a Toast, Snackbar, or a Dialog.
        if (won) {
            // Show winning message
        } else {
            // Show losing message
        }
    }
}
