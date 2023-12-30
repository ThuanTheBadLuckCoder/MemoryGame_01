package com.example.mymemorygame01

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Game02Activity : AppCompatActivity() {
    private lateinit var cardImageViews: Array<ImageView>
    private val cardImages = mutableListOf(
        "card01", "card02", "card03", "card04", "card05",
        "card06", "card07", "card08"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game02_activity)

        cardImageViews = arrayOf(
            findViewById(R.id.cardImageView1),
            findViewById(R.id.cardImageView2),
            findViewById(R.id.cardImageView3)
        )

        showRandomCards()
    }

    private fun showRandomCards() {
        // Trộn danh sách lá bài
        cardImages.shuffle()

        // Hiển thị 3 lá bài đầu tiên từ danh sách đã được trộn
        for (i in cardImageViews.indices) {
            val cardImageView = cardImageViews[i]
            cardImageView.tag = cardImages[i]
            cardImageView.visibility = View.VISIBLE
            val resourceId = resources.getIdentifier(cardImages[i], "drawable", packageName)
            cardImageView.setImageResource(resourceId)
        }
    }
}