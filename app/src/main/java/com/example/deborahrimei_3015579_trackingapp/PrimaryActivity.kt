package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PrimaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_primary)

        // retrieve all edit texts and buttons from the layout
        var primary_name: EditText = findViewById(R.id.primary_et_name)
        var primary_identity : EditText = findViewById(R.id.primary_et_identity)
        var primary_button: Button = findViewById(R.id.primary_button)

        //create new SharedPreferences
        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        var firstTime = preferences.getString("FirstTimeInstall", "")

        // if the primary activity has already been viewed, run this code
        if (firstTime.equals("Yes")) {
            Intent(this@PrimaryActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            //if the primary activity needs to be viewed for the first time, run this code
        } else {
            var editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("FirstTimeInstall", "Yes")
            editor.apply()

            //when the button is clicked, the next activity will be displayed (Main)
            primary_button.setOnClickListener {
                val msg: String = primary_name.text.toString()

                //if the EditText are not empty, pass the message in the SharedPreferences
                if (msg.trim().isNotBlank() || msg.trim().isNotEmpty()) {
                    Intent(this@PrimaryActivity, MainActivity::class.java).also {
                        editor.putString("UserName", primary_name.text.toString())
                        editor.putString("Identity", primary_identity.text.toString())
                        editor.commit()
                        startActivity(it)
                        finish()
                    }
                    // in case the user did not enter anything, display an error message
                } else {
                    Toast.makeText(applicationContext, "Please fill the tabs", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}