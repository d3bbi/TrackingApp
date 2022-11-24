package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations

class CreateActivity : AppCompatActivity() {

    private lateinit var buttonSaveHabit: Button
    private lateinit var habitNameEditText: EditText
    private lateinit var habitReasonEditText: EditText
    private lateinit var isCompleted: CheckBox
    private lateinit var date: String


    // create an habit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        //change name of support bar
        supportActionBar?.setTitle("Create Habit")

        habitNameEditText = findViewById(R.id.create_et_name)
        habitReasonEditText = findViewById(R.id.create_et_reason)


        //when click on save button,data will be saved and display on main activity
//        buttonSaveHabit = findViewById(R.id.crete_btn_save)
//        buttonSaveHabit.setOnClickListener {
//            Intent(this@CreateActivity, MainActivity::class.java).also {
//                it.putExtra("EXTRA_NAME", habitNameEditText.text.toString())
//                it.putExtra("EXTRA_GOAL", habitReasonEditText.text.toString())
//                startActivity(it)
//            }
//        }
    }

    /* === SAVE HABIT === */
    public fun saveHabitAction(view: View) {
        val habitName = habitNameEditText.text.toString()
        val habitReason = habitReasonEditText.text.toString()
        val newHabit = Habit(habitName, habitReason, false)

        val dbo = DatabaseOperations(this)
        dbo.addItem(dbo, newHabit)

        //return to the home page
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }


    }

}


