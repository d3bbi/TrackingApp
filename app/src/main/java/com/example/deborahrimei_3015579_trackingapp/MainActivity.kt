package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    // variable used
    private var _buttonCreatehabit: Button? = null
    private var _buttonSeeCalendar: Button? = null
    private var _initialIdentity: TextView? = null
    private var _initialName : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* START CREATE HABIT ACTIVITY
        * Button used to create an activity, when clicked the next activity will be showen*/
        _buttonCreatehabit = findViewById(R.id.main_btn_create)
        _buttonCreatehabit?.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivity(intent)
        }

        /* START CALENDAR ACTIVITY
        * When button is clicked, the calendar will be showen */
        _buttonSeeCalendar = findViewById(R.id.main_btn_calendar)
        _buttonSeeCalendar?.setOnClickListener {
            val intent = Intent(this@MainActivity, StatusActivity::class.java)
            startActivity(intent)
        }

        /* SHARE INFORMATION WITH THE MAIN ACTIVITY
        * Using shared preference we get the name and identity of a user*/
        _initialIdentity = findViewById(R.id.main_tv_identity)
        _initialName = findViewById(R.id.main_tv_title)

        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        var _identity = preferences.getString("Identity", "").toString()
        var _name = preferences.getString("Username", "").toString()
        _initialIdentity?.setText(_identity)
        _initialName?.setText("Hi $_name")


    }


}