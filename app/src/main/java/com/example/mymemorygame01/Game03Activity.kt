package com.example.mymemorygame01

import android.os.Bundle
import android.os.Handler
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Game03Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game03_activity)
        displayRandomCards()
    }

    private fun displayRandomCards() {
        // Danh sách chứa ID của tất cả các hình ảnh từ card01 đến card21
        val cardResourceIds = listOf(
            R.drawable.card01, R.drawable.card02, R.drawable.card03,
            R.drawable.card04, R.drawable.card05, R.drawable.card06,
            R.drawable.card07, R.drawable.card08, R.drawable.card09,
            R.drawable.card10, R.drawable.card11, R.drawable.card12,
            R.drawable.card13, R.drawable.card14, R.drawable.card15,
            R.drawable.card16, R.drawable.card17, R.drawable.card18,
            R.drawable.card19, R.drawable.card20, R.drawable.card21
        ).shuffled().take(4) // Lấy ngẫu nhiên 4 ID từ danh sách

        // Gán tài nguyên hình ảnh cho từng ImageView
        findViewById<ImageView>(R.id.imageView1).setImageResource(cardResourceIds[0])
        findViewById<ImageView>(R.id.imageView2).setImageResource(cardResourceIds[1])
        findViewById<ImageView>(R.id.imageView3).setImageResource(cardResourceIds[2])
        findViewById<ImageView>(R.id.imageView4).setImageResource(cardResourceIds[3])
    }
}
