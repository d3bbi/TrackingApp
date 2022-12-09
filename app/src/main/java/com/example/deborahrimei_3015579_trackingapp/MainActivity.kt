package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseQueries
import com.example.deborahrimei_3015579_trackingapp.habits.CreateHabitActivity
import com.example.deborahrimei_3015579_trackingapp.habits.Habit
import com.example.deborahrimei_3015579_trackingapp.habits.HabitMainAdapter
import com.example.deborahrimei_3015579_trackingapp.habits.StatusActivity
import com.example.deborahrimei_3015579_trackingapp.user.User
import com.example.deborahrimei_3015579_trackingapp.user.UserProfileActivity
import java.time.Period

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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        /* === ADD HABITS IN THE DATABASE === */
        var dbo = DatabaseOperations(this)
        val cursorHabits = dbo.getAllHabits(dbo)

        with(cursorHabits) {
            while (moveToNext()) {
                val habitName =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_NAME))
                val habitReason =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_REASON))
                val habitCompletion =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_COMPLETION))
                val habitIsCompleted = if (habitCompletion.toInt() == 0) false else true
                val habitPeriodYear = getInt(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_PERIOD_YEAR))
                val habitPeriodMonth = getInt(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_PERIOD_MONTH))
                val habitPeriodDay = getInt(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_PERIOD_DAY))
                val habitDate =
                    getString(getColumnIndexOrThrow(DatabaseQueries.HabitTable.COLUMN_HABIT_CREATION_DATE))
                val period = Period.of(habitPeriodYear, habitPeriodMonth, habitPeriodDay)

                habitList.add(Habit(habitName, habitReason, habitIsCompleted, period, habitDate))
                dbo.close()
            }
            for (i in habitList) Log.d("HABIT", "MAIN ACTIVITY _ HABIT ${i.toString()}")
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
            val intent: Intent = Intent(this@MainActivity, CreateHabitActivity::class.java)
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

//        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
//        var identity = preferences.getString("Identity", "").toString()
//        var userName = preferences.getString("Username", "").toString()

        dbo = DatabaseOperations(this)
        val cursorUser = dbo.getUser(dbo)
        var initialUserName: String = ""
        var initialIdentity: String = ""

        with(cursorUser) {
            while (moveToNext()) {
                initialUserName =
                    getString(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_NAME))
                initialIdentity =
                    getString(getColumnIndexOrThrow(DatabaseQueries.UserTable.COLUMN_USER_IDENTITY))
            }
        }

        var user = User(initialUserName, initialIdentity, "Pic".toByteArray(), 0, 0)
        dbo.addUser(dbo, user)

        initialName.setText(user.name)
        this@MainActivity.initialIdentity.setText(user.identity)
        dbo.close()

        buttonProfile = findViewById(R.id.main_btn_profile)
        buttonProfile.setOnClickListener {
            val intent = Intent(this@MainActivity, UserProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
