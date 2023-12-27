package com.example.mymemorygame01

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.gridlayout.widget.GridLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private val gridSize = 6 // 6x6 grid
    private val buttons = Array(gridSize) { arrayOfNulls<Button>(gridSize) }
    private val correctButtons = mutableListOf<Button>()
    private val handler = Handler()
    private var playerScore = 60
    private var penalty = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)

        initializeGrid()
        // displayRandomColors() không còn được gọi ở đây nữa
    }

    private fun initializeGrid() {
        gridLayout.post {
            val gridWidth = gridLayout.width - (gridLayout.paddingLeft + gridLayout.paddingRight)
            val gridHeight = gridLayout.height - (gridLayout.paddingTop + gridLayout.paddingBottom)

            val buttonSize = Math.min(gridWidth, gridHeight) / gridSize

            for (i in 0 until gridSize) {
                for (j in 0 until gridSize) {
                    buttons[i][j] = Button(this).apply {
                        layoutParams = GridLayout.LayoutParams().apply {
                            width = buttonSize - 10 * 2
                            height = buttonSize - 10 * 2
                            setMargins(5, 5, 5, 5)
                            // Chỉnh layout_gravity để căn giữa theo chiều ngang và chiều dọc
                            gravity = Gravity.CENTER
                        }
                        setBackgroundColor(Color.WHITE)
                        setOnClickListener {
                            checkButton(this)
                        }
                    }
                    gridLayout.addView(buttons[i][j])
                }
            }

            displayRandomColors()
        }
    }



    private fun displayRandomColors() {
        val random = Random(System.currentTimeMillis())
        val numberOfColors = 5 // Number of colored blocks to show

        correctButtons.clear()
        for (button in gridLayout.children) {
            (button as Button).setBackgroundColor(Color.WHITE)
        }

        repeat(numberOfColors) {
            val i = random.nextInt(gridSize)
            val j = random.nextInt(gridSize)
            val button = buttons[i][j]!!
            button.setBackgroundColor(Color.YELLOW)
            correctButtons.add(button)
        }

        handler.postDelayed({
            correctButtons.forEach { it.setBackgroundColor(Color.WHITE) }
        }, 3000)
    }

    private fun checkButton(button: Button) {
        if (button in correctButtons) {
            button.setBackgroundColor(Color.GREEN)
        } else {
            button.setBackgroundColor(Color.RED)
            playerScore -= penalty
            penalty += 10 // Tăng hình phạt cho lần sai tiếp theo

            if (playerScore <= 0) {
                endGame()
            }
        }
    }

    private fun endGame() {
        Toast.makeText(this, "Game Over! Your score: $playerScore", Toast.LENGTH_LONG).show()
    }
}
