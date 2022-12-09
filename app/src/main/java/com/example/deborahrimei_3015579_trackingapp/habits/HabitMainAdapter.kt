package com.example.deborahrimei_3015579_trackingapp.habits

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.deborahrimei_3015579_trackingapp.MainActivity
import com.example.deborahrimei_3015579_trackingapp.R
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations

class HabitMainAdapter(private val habitList: ArrayList<Habit>, val activity: MainActivity) :
    RecyclerView.Adapter<HabitMainAdapter.ViewHolder>() {

    //create class ViewHolder
    class ViewHolder(val habitlayout: RelativeLayout) : RecyclerView.ViewHolder(habitlayout) {
    }

    // this function will return the View in the activity
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_layout, parent, false) as RelativeLayout

        /* === EDIT HABIT WHEN CLICKED === */
        habitLayout.setOnClickListener {
            val index = parent.indexOfChild(it)
            val intent: Intent = Intent(parent.context, CreateHabitActivity::class.java)
            intent.putExtra("HabitNameExtra", habitList.get(index).name)
            intent.putExtra("HabitReasonExtra", habitList.get(index).reason)
            intent.putExtra("HabitPeriodExtra", habitList.get(index).period.toString())
            intent.putExtra("HabitCreationDateExtra", habitList.get(index).creationDate)
            parent.context.startActivity(intent)
        }

        /* === DELETE HABIT WHEN LONG CLICKED === */
        habitLayout.setOnLongClickListener {
            val position: Int = parent.indexOfChild(it)
            val dbo = DatabaseOperations(parent.context)
            val habitToRemove = habitList[position]

            dbo.deleteHabit(dbo, habitToRemove)

            habitList.removeAt(position)
            notifyItemRemoved(position)
            true
        }


        /*LISTEN WHEN CHECKBOX IS CHECKED*/
        val checkBox: CheckBox = habitLayout.findViewById(R.id.habit_cb_completion)

        checkBox.setOnClickListener {
            val index: Int = parent.indexOfChild(it)
            Log.d("INDEX", "CHECKBOX $index and ${parent.indexOfChild(it)}")
//            val newHabit: Habit = habitList.get(index)
//            newHabit.isCompleted = true
//
//            val dbo = DatabaseOperations(parent.context)
//            dbo.updateHabit(dbo, habitList.get(index), newHabit)
//
//            dbo.close()
        }
        return ViewHolder(habitLayout)
    }


    //bind the view
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habitBindLayout = holder.habitlayout

        val nameTextView = habitBindLayout.getChildAt(0) as TextView
        val dateTextView = habitBindLayout.getChildAt(1) as TextView
        val completionCheckBox = habitBindLayout.getChildAt(2) as CheckBox

        nameTextView.text = habitList[position].name
        dateTextView.text = habitList[position].getDateAsString()
        completionCheckBox.isChecked = habitList[position].isCompleted
    }

    override fun getItemCount(): Int {
        return habitList.size
    }


}