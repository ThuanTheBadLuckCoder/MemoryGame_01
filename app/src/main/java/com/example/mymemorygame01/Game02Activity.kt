package com.example.mymemorygame01

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Game02Activity : AppCompatActivity() {
    private lateinit var cardImageViews: Array<ImageView>
    private val cardImages = mutableListOf(
        "card01", "card02", "card03", "card04", "card05",
        "card06", "card07", "card08", "card09", "card10",
        "card11", "card12", "card13", "card14", "card15",
        "card16", "card17", "card18", "card19", "card20",
        "card21"
    )
    private val selectedCards = mutableListOf<String>()
    private var round = 1
    private var lastRoundCards = mutableListOf<String>()

    private var recentCards = ArrayDeque<String>(2)
    private var cardAbsenceTracker = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game02_activity)

        cardImageViews = arrayOf(
            findViewById(R.id.cardImageView1),
            findViewById(R.id.cardImageView2),
            findViewById(R.id.cardImageView3),
            findViewById(R.id.cardImageView4),
            findViewById(R.id.cardImageView5)
        )

        initGame()
    }

    private fun initGame() {
        // Đảm bảo rằng 5 lá bài được chọn ngẫu nhiên không trùng lặp
        selectedCards.clear()
        lastRoundCards.clear()
        cardAbsenceTracker.clear()
        recentCards.clear()

        val shuffledCards = cardImages.shuffled()
        for (i in 0 until 5) {
            selectedCards.add(shuffledCards[i])
        }
        updateCardDisplay()
    }

    private fun updateCardDisplay() {
        for (i in cardImageViews.indices) {
            val cardImageView = cardImageViews[i]
            val cardName = selectedCards[i]
            cardImageView.tag = cardName
            cardImageView.visibility = View.VISIBLE
            val resourceId = resources.getIdentifier(cardName, "drawable", packageName)
            cardImageView.setImageResource(resourceId)
        }
    }

    fun onCardClick(view: View) {
        if (view is ImageView) {
            val cardName = view.tag as String
            val isCorrectChoice = round == 1 || !lastRoundCards.contains(cardName)

            if (isCorrectChoice) {
                processCorrectSelection(cardName)
            } else {
                showLostDialog()
            }
        }
    }


    private fun processCorrectSelection(cardName: String) {
        // Cập nhật lastRoundCards và chuẩn bị cho round tiếp theo
        if (round == 1) {
            lastRoundCards.clear()
            lastRoundCards.addAll(selectedCards.filter { it != cardName })
        }

        prepareNextRound(cardName)
        round++
    }

    private fun prepareNextRound(selectedCard: String) {
        // Chọn một lá bài mới và trộn với các lá bài cũ
        val newCards = if (round == 1) {
            (cardImages - selectedCards).shuffled().take(1)
        } else {
            listOf(selectedCard)
        }

        selectedCards.clear()
        selectedCards.addAll(lastRoundCards)
        selectedCards.addAll(newCards)
        selectedCards.shuffle()

        updateCardDisplay()
    }

    private fun showLostDialog() {
        Toast.makeText(this, "Lost dialog should appear", Toast.LENGTH_SHORT).show()
        // Rest of your code for showing the dialog
        // ...
    }


    private fun updateCardAbsence(selectedCard: String) {
        cardAbsenceTracker.keys.forEach { card ->
            if (card != selectedCard) {
                cardAbsenceTracker[card] = (cardAbsenceTracker[card] ?: 0) + 1
            }
        }
    }

}