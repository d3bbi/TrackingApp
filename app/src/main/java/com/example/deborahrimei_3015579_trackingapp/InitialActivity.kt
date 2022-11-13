package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InitialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        // retrieve all edit texts and buttons from the layout
        var initial_name: EditText = findViewById(R.id.initial_et_name)
        var initial_identity : EditText = findViewById(R.id.initiail_et_identity)
        var initial_button: Button = findViewById(R.id.primary_button)

        //create new SharedPreferences
        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        var firstTime = preferences.getString("FirstTimeInstall", "")

        // if the primary activity has already been viewed, run this code
        if (firstTime.equals("Yes")) {
            Intent(this@InitialActivity, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            //if the primary activity needs to be viewed for the first time, run this code
        } else {
            var editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("FirstTimeInstall", "Yes")
            editor.apply()

            //when the button is clicked, the next activity will be displayed (Main)
            initial_button.setOnClickListener {
                val msg: String = initial_name.text.toString()

                //if the EditText are not empty, pass the message in the SharedPreferences
                if (msg.trim().isNotBlank() || msg.trim().isNotEmpty()) {
                    Intent(this@InitialActivity, MainActivity::class.java).also {
                        editor.putString("Username", initial_name.text.toString())
                        editor.putString("Identity", initial_identity.text.toString())
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