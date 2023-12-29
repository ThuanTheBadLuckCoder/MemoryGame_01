package com.example.mymemorygame01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonChoice1 = findViewById<Button>(R.id.buttonChoice1)
        buttonChoice1.setOnClickListener {
            val intent = Intent(this, Game01Activity::class.java)
            startActivity(intent)
        }
    }
}

