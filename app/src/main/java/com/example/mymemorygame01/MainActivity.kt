import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.gridlayout.widget.GridLayout
import com.example.mymemorygame01.R
import kotlin.random.Random

class MemoryGameActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private val gridSize = 6 // 6x6 grid
    private val buttons = Array(gridSize) { arrayOfNulls<Button>(gridSize) }
    private val correctButtons = mutableListOf<Button>()
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)

        initializeGrid()
        displayRandomColors()
    }

    private fun initializeGrid() {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                buttons[i][j] = Button(this).apply {
                    val button = this
                    layoutParams = GridLayout.LayoutParams(GridLayout.spec(i, GridLayout.FILL, 1f), GridLayout.spec(j, GridLayout.FILL, 1f)).apply {
                        width = 0
                        height = 0
                        setMargins(5, 5, 5, 5)
                    }
                    setBackgroundColor(Color.WHITE)
                    setOnClickListener {
                        checkButton(button)
                    }
                }
                gridLayout.addView(buttons[i][j])
            }
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
        }
        // Logic for ending the game or scoring can go here
    }
}
