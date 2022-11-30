package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseQueries
import com.example.deborahrimei_3015579_trackingapp.habits.CreateActivity
import com.example.deborahrimei_3015579_trackingapp.habits.Habit
import com.example.deborahrimei_3015579_trackingapp.habits.HabitMainAdapter
import com.example.deborahrimei_3015579_trackingapp.habits.StatusActivity
import com.example.deborahrimei_3015579_trackingapp.user.UserProfile

class MainActivity : AppCompatActivity() {

    // variable used
    private lateinit var buttonCreatehabit: Button
    private lateinit var buttonSeeCalendar: Button
    private lateinit var buttonProfile: Button
    private lateinit var initialIdentity: TextView
    private lateinit var initialName: TextView

    // variable recycler view
    private lateinit var habitRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: HabitMainAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    var habitList = ArrayList<Habit>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        /* === ADD HABITS IN THE DATABASE === */
        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllHabits(dbo)

        with(cursor) {
            while (moveToNext()) {
                val habitName =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_NAME))
                val habitReason =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_REASON))
                val habitCompletion =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_COMPLETION))
                val habitIsCompleted = if (habitCompletion.toInt() == 0) false else true
                val habitDate =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_DATE))

                habitList.add(Habit(habitName, habitReason, habitIsCompleted))
                dbo.close()
            }
            for (i in habitList) Log.d("HABIT", "${i.toString()}")
        }

        /* === ADD HABITS IN THE RECYCLER VIEW === */
        habitRecyclerView = findViewById(R.id.habits_recycler_view)
        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = HabitMainAdapter(habitList, this)
        habitRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }


        /* START CREATE HABIT ACTIVITY
        * Button used to create an activity, when clicked the next activity will be showen*/
        buttonCreatehabit = findViewById(R.id.main_btn_create)
        buttonCreatehabit.setOnClickListener {
            val intent: Intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivity(intent)
            finish()
        }

        /* START CALENDAR ACTIVITY
        * When button is clicked, the calendar will be showen */
        buttonSeeCalendar = findViewById(R.id.main_btn_calendar)
        buttonSeeCalendar.setOnClickListener {
            val intent = Intent(this@MainActivity, StatusActivity::class.java)
            startActivity(intent)
        }

        /* === SHARE INITIAL INFORMATION WITH THE MAIN ACTIVITY ===
        * Using shared preference we get the name and identity of a user*/
        initialIdentity = findViewById(R.id.main_tv_identity)
        initialName = findViewById(R.id.main_tv_title)

        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        var _identity = preferences.getString("Identity", "").toString()
        var _name = preferences.getString("Username", "").toString()
        initialIdentity.setText(_identity)
        initialName.setText("Hi $_name")


        buttonProfile = findViewById(R.id.main_btn_profile)
        buttonProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, UserProfile::class.java)
            startActivity(intent)
        }

    }
}
