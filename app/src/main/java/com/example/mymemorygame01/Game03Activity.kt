package com.example.mymemorygame01

import android.os.Bundle
import android.os.Handler
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Game03Activity : AppCompatActivity() {
    private lateinit var imageViews: Array<ImageView>
    private var cardResourceIds = mutableListOf<Int>()
    private var hiddenCardIndex = 0

    private lateinit var answerImageViews: Array<ImageView>
    private var hiddenCardResourceId = 0
    private var isRoundOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game03_activity)
        setupImageViews()

        answerImageViews = arrayOf(
            findViewById(R.id.answerImageView1),
            findViewById(R.id.answerImageView2),
            findViewById(R.id.answerImageView3),
            findViewById(R.id.answerImageView4)
        )

        // Set up listeners for answer choices
        setupAnswerListeners()

        // Start the game
        startGame()
    }

    private fun setupImageViews() {
        imageViews = arrayOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4)
        )
    }

    private fun startGame() {
        // Display random cards
        displayRandomCards()
    }

    private fun displayRandomCards() {
        cardResourceIds = listOf(
            R.drawable.card01, R.drawable.card02, R.drawable.card03,
            R.drawable.card04, R.drawable.card05, R.drawable.card06,
            R.drawable.card07, R.drawable.card08, R.drawable.card09,
            R.drawable.card10, R.drawable.card11, R.drawable.card12,
            R.drawable.card13, R.drawable.card14, R.drawable.card15,
            R.drawable.card16, R.drawable.card17, R.drawable.card18,
            R.drawable.card19, R.drawable.card20, R.drawable.card21
        ).shuffled().take(4).toMutableList()

        // Set images for the imageViews
        cardResourceIds.forEachIndexed { index, resourceId ->
            imageViews[index].setImageResource(resourceId)
        }

        // Hide a random card after 3 seconds
        Handler().postDelayed({
            hideRandomCard()
        }, 3000) // Delay for 3 seconds
    }

    private fun hideRandomCard() {
        // Choose a random card to hide
        hiddenCardIndex = Random.nextInt(4)

        // Store the hidden card's resource ID
        hiddenCardResourceId = cardResourceIds[hiddenCardIndex]

        // Replace the image with cardquestion.png
        imageViews[hiddenCardIndex].setImageResource(R.drawable.cardquestion)

        // Delay before revealing answers
        Handler().postDelayed({
            revealAnswers()
        }, 3000) // Delay for 3 seconds
    }


    private fun setupAnswerListeners() {
        answerImageViews.forEach { imageView ->
            imageView.setOnClickListener {
                if (!isRoundOver) {
                    checkAnswer(imageView)
                }
            }
        }
    }

    private fun checkAnswer(selectedImageView: ImageView) {
        val selectedAnswerId = selectedImageView.drawable.constantState

        if (selectedAnswerId == resources.getDrawable(hiddenCardResourceId).constantState) {
            // User selected the correct answer
            Toast.makeText(this, "Correct! Preparing next round...", Toast.LENGTH_SHORT).show()
            isRoundOver = true
            // Delay before starting a new round
            Handler().postDelayed({
                // Clear the answers and start a new round
                resetGame()
            }, 2000) // Delay for 2 seconds
        } else {
            // User selected the wrong answer
            Toast.makeText(this, "Wrong answer! Game Over.", Toast.LENGTH_SHORT).show()
            // You can add an option to play again here
        }
    }

    private fun resetGame() {
        answerImageViews.forEach {
            imageView -> imageView.setImageResource(R.drawable.cardquestion)
        }

        // Clear the card resource IDs and images
        cardResourceIds.clear()
        imageViews.forEach { imageView ->
            imageView.setImageResource(0) // Clear the images
        }
        isRoundOver = false
        // Start a new round by displaying random cards and hiding a random card
        startGame()
    }

    private fun revealAnswers() {
        // Show the answers
        answerImageViews.forEachIndexed { index, resourceId ->
            answerImageViews[index].setImageResource(cardResourceIds[index])
        }

    }
}