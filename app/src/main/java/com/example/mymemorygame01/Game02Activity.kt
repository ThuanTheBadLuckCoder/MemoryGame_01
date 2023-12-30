package com.example.mymemorygame01

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Game02Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game02_activity)

        // Gọi hàm randomChoose() để chọn ngẫu nhiên hình ảnh cho ImageView thứ hai
        randomChoose()
    }

    private fun randomChoose() {
        val cardImageView = findViewById<ImageView>(R.id.cardImageView)

        val cardImages = arrayOf("card_1", "card_2", "card_3", "card_4", "card_5", "card_6", "card_7", "card_8", "card_9")
        val randomImageName = cardImages.random()
        val resourceId = resources.getIdentifier(randomImageName, "drawable", packageName)
        cardImageView.setImageResource(resourceId)
    }
}

