package com.example.mymemorygame01

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Game02Activity : AppCompatActivity() {
    private lateinit var cardImageViews: Array<ImageView>
    private val cardImages = mutableListOf(
        // Danh sách tên hình ảnh của các thẻ
        "card01", "card02", "card03", "card04", "card05",
        "card06", "card07", "card08", "card09", "card10",
        "card11", "card12", "card13", "card14", "card15",
        "card16", "card17", "card18", "card19", "card20",
        "card21"
    )
    private val selectedCards = mutableListOf<String>()
    private var round = 1
    private var lastSelectedCard: String? = null
    private var previousRoundCards = mutableListOf<String>()

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
        selectedCards.clear()
        lastSelectedCard = null
        selectedCards.addAll(cardImages.shuffled().take(5))
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
            processSelection(cardName)
        }
    }

    private fun processSelection(cardName: String) {
        if (round == 1) {
            prepareNextRound(cardName)
            round++
        } else if (cardName == lastSelectedCard) {
            Toast.makeText(this, "Correct! Moving to next round.", Toast.LENGTH_SHORT).show()
            prepareNextRound(cardName)
            round++
        } else {
            showLostDialog()
        }
    }

    private fun prepareNextRound(selectedCard: String) {
        // Trong vòng đầu tiên, chỉ đặt lastSelectedCard là lá bài được chọn
        if (round == 1) {
            lastSelectedCard = selectedCard
            return
        }

        // Từ vòng thứ hai trở đi, random một lá bài mới không trùng với các lá bài hiện tại và lá bài được chọn
        val availableCards = (cardImages - selectedCards - selectedCard)
        val newCard = availableCards.shuffled().first()

        // Cập nhật lastSelectedCard và danh sách các lá bài cho vòng tiếp theo
        lastSelectedCard = newCard
        selectedCards.remove(selectedCard) // Loại bỏ lá bài được chọn ở vòng trước
        selectedCards.add(newCard)         // Thêm lá bài mới
        selectedCards.shuffle()            // Trộn lẫn vị trí của các lá bài

        updateCardDisplay()
    }



    private fun showLostDialog() {
        // Vô hiệu hóa click vào các ImageView
        cardImageViews.forEach { imageView ->
            imageView.isClickable = false
        }

        // Tạo và hiển thị AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("You lost the game. Do you want to exit?")
            .setPositiveButton("Exit") { dialog, which ->
                // Thoát khỏi Activity
                finish()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                // Nếu người chơi chọn 'Cancel', cho phép chơi lại
                dialog.dismiss()
                initGame()
            }
            .setCancelable(false)
            .show()
    }
}