package com.example.mymemorygame01

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Game03Activity : AppCompatActivity() {

    private lateinit var cardsGridLayout: GridLayout
    private var cards = mutableListOf<Int>()
    private var visibleCards = mutableListOf<Int>()
    private var correctCardIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game03_activity)

        cardsGridLayout = findViewById(R.id.cardsGridLayout)
        loadCards()
        displayCards()

        val btnRevealAnswer: Button = findViewById(R.id.btnRevealAnswer)
        btnRevealAnswer.setOnClickListener {
            revealAnswer()
        }
    }

    private fun loadCards() {
        // Load all card resources into the list (this is just a placeholder code, adjust it based on your actual drawable names)
        for (i in 1..21) {
            val cardId = resources.getIdentifier("card$i", "drawable", packageName)
            cards.add(cardId)
        }
    }

    private fun displayCards() {
        // Randomly select four cards to display
        visibleCards = cards.shuffled().take(4).toMutableList()

        // Randomly select one card to be the correct answer
        correctCardIndex = Random.nextInt(visibleCards.size)

        // Clear previous cards
        cardsGridLayout.removeAllViews()

        // Add ImageView widgets to GridLayout
        for (i in 0 until 2) { // 2 rows
            for (j in 0 until 2) { // 2 columns
                val cardIndex = i * 2 + j
                val cardImageView = ImageView(this).apply {
                    setImageResource(visibleCards[cardIndex])
                    layoutParams = GridLayout.LayoutParams(GridLayout.spec(i, 1f), GridLayout.spec(j, 1f)).apply {
                        width = 0
                        height = 0
                        rightMargin = 15
                        bottomMargin = 15
                    }
                    id = View.generateViewId()
                    setOnClickListener { onCardClick(it) }
                }
                cardsGridLayout.addView(cardImageView)
            }
        }
    }

    private fun onCardClick(view: View) {
        // Logic when a card is clicked can be implemented here
    }

    private fun revealAnswer() {
        // Hide all cards and show a question mark instead after a delay
        Handler().postDelayed({
            for (i in 0 until cardsGridLayout.childCount) {
                val cardImageView = cardsGridLayout.getChildAt(i) as ImageView
                if (i == correctCardIndex) {
                    cardImageView.setImageResource(R.drawable.cardquestion)
                } else {
                    cardImageView.visibility = View.INVISIBLE
                }
            }
        }, 3000) // Hide after 3 seconds
    }
}