package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var _button : Button = findViewById(R.id.create_button)
        _button.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, CreateHabit::class.java)
            startActivity(intent)
        }
    }
}