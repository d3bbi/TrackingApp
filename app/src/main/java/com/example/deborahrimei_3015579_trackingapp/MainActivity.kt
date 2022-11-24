package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseQueries

class MainActivity : AppCompatActivity() {

    // variable used
    private lateinit var buttonCreatehabit: Button
    private lateinit var buttonSeeCalendar: Button
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

        /*ADD HABITS IN THE RECYCLER VIEW*/
        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)

        with(cursor){
            while (moveToNext()){
                val habitName = getString(getColumnIndexOrThrow(DatabaseQueries.TableInfo.COLUMN_HABIT_NAME))
                val habitReason = getString(getColumnIndexOrThrow(DatabaseQueries.TableInfo.COLUMN_HABIT_REASON))
                val habitCompletion = getString(getColumnIndexOrThrow(DatabaseQueries.TableInfo.COLUMN_HABIT_COMPLETION))
                val habitIsCompleted = if (habitCompletion.equals(0)) false else true
                val habitDate = getString(getColumnIndexOrThrow(DatabaseQueries.TableInfo.COLUMN_DATE))

                habitList.add(Habit(habitName, habitReason, habitIsCompleted))
            }
        }
        habitList.add(Habit("Loundry", "Cause needs to do", false))

        habitRecyclerView = findViewById(R.id.habits_recycler_view)

        //specified that we want a linearLayout
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
        }

        /* START CALENDAR ACTIVITY
        * When button is clicked, the calendar will be showen */
        buttonSeeCalendar = findViewById(R.id.main_btn_calendar)
        buttonSeeCalendar.setOnClickListener {
            val intent = Intent(this@MainActivity, StatusActivity::class.java)
            startActivity(intent)
        }

        /* SHARE INFORMATION WITH THE MAIN ACTIVITY
        * Using shared preference we get the name and identity of a user*/
        initialIdentity = findViewById(R.id.main_tv_identity)
        initialName = findViewById(R.id.main_tv_title)

        var preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        var _identity = preferences.getString("Identity", "").toString()
        var _name = preferences.getString("Username", "").toString()
        initialIdentity.setText(_identity)
        initialName.setText("Hi $_name")


    }


}