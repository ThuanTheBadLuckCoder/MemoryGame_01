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
        displayRandomCards()
        Handler().postDelayed({
            setupAnswerChoices()
            setupAnswerListeners()
        }, 3000)
    }
    private fun setupImageViews() {
        imageViews = arrayOf(
            findViewById(R.id.imageView1),
            findViewById(R.id.imageView2),
            findViewById(R.id.imageView3),
            findViewById(R.id.imageView4)
        )
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

        cardResourceIds.forEachIndexed { index, resourceId ->
            imageViews[index].setImageResource(resourceId)
        }

        hideRandomCard()
    }

    private fun hideRandomCard() {
        // Chọn ngẫu nhiên một thẻ để ẩn
        hiddenCardIndex = Random.nextInt(4)

        // Thay thế hình ảnh đó bằng cardquestion.png sau 3 giây
        Handler().postDelayed({
            imageViews[hiddenCardIndex].setImageResource(R.drawable.cardquestion)
        }, 3000) // Độ trễ 3000ms (3 giây)
    }

    private fun setupAnswerChoices() {
        // Lấy ID tài nguyên của thẻ bị ẩn
        hiddenCardResourceId = cardResourceIds[hiddenCardIndex]

        // Lấy danh sách tất cả các ID tài nguyên thẻ từ card01 đến card21


        val allCardResourceIds = listOf(
            R.drawable.card01, R.drawable.card02, R.drawable.card03,
            R.drawable.card04, R.drawable.card05, R.drawable.card06,
            R.drawable.card07, R.drawable.card08, R.drawable.card09,
            R.drawable.card10, R.drawable.card11, R.drawable.card12,
            R.drawable.card13, R.drawable.card14, R.drawable.card15,
            R.drawable.card16, R.drawable.card17, R.drawable.card18,
            R.drawable.card19, R.drawable.card20, R.drawable.card21
        ).shuffled().take(4).toMutableList()

        // Lọc ra các ID không phải là những thẻ đã hiển thị ban đầu
        val possibleWrongAnswers = allCardResourceIds - cardResourceIds

        // Chọn ngẫu nhiên ba đáp án sai
        val wrongAnswers = possibleWrongAnswers.shuffled().take(3).toMutableList()

        // Thêm đáp án đúng vào danh sách đáp án và trộn chúng
        wrongAnswers.add(hiddenCardResourceId)
        wrongAnswers.shuffle()

        // Đặt hình ảnh cho các ImageView trong phần đáp án
        wrongAnswers.forEachIndexed { index, resourceId ->
            answerImageViews[index].setImageResource(resourceId)
        }
    }


    private fun setupAnswerListeners() {
        answerImageViews.forEach { imageView ->
            imageView.setOnClickListener {
                checkAnswer(imageView)
            }
        }
    }

    private fun checkAnswer(selectedImageView: ImageView) {
        val selectedAnswerId = selectedImageView.drawable.constantState

        if (selectedAnswerId == resources.getDrawable(hiddenCardResourceId).constantState) {
            // Người dùng chọn đúng
            Toast.makeText(this, "Correct! Preparing next round...", Toast.LENGTH_SHORT).show()

            // Thời gian chờ trước khi bắt đầu vòng mới
            Handler().postDelayed({
                displayRandomCards() // Hiển thị lại các thẻ mới
            }, 2000) // Độ trễ 2000ms (2 giây)
        } else {
            // Người dùng chọn sai
            Toast.makeText(this, "Wrong answer! Game Over.", Toast.LENGTH_SHORT).show()
            // Có thể thêm lựa chọn để chơi lại ở đây
        }
    }

}
