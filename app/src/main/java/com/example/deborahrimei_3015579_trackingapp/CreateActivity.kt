package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateActivity : AppCompatActivity() {

    private var _buttonSaveHabit: Button? = null
    private var _name: EditText? = null
    private var _goal: EditText? = null
    private var _person: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        //when click on save button,data will be saved and display on main activity
        _buttonSaveHabit = findViewById(R.id.crete_btn_save)
        _buttonSaveHabit?.setOnClickListener {
            _name = findViewById(R.id.create_et_name)
            _goal = findViewById(R.id.create_et_goal)
            _person = findViewById(R.id.create_et_person)

            Intent(this@CreateActivity, MainActivity::class.java).also {
                it.putExtra("EXTRA_NAME", _name?.text.toString())
                it.putExtra("EXTRA_GOAL", _goal?.text.toString())
                it.putExtra("EXTRA_PERSON", _person?.text.toString())
                startActivity(it)
            }
        }
    }

}


