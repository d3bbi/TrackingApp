package com.example.deborahrimei_3015579_trackingapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deborahrimei_3015579_trackingapp.database.DatabaseOperations

class HabitMainAdapter(private val habitList: ArrayList<Habit>, val activity: MainActivity) :
    RecyclerView.Adapter<HabitMainAdapter.ViewHolder>() {

    //create class ViewHolder
    class ViewHolder(val habitlayout: RelativeLayout) : RecyclerView.ViewHolder(habitlayout) {
    }

    // this fun will return the View in the activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val habitLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_layout, parent, false) as RelativeLayout

        /* === DELETE HABIT WHEN LONG CLICK IS SET === */
        habitLayout.setOnLongClickListener{
            val position: Int = parent.indexOfChild(it)

            val habitToRemove = habitList[position]
            val dbo = DatabaseOperations(parent.context)
            dbo.deleteHabit(dbo, habitToRemove)

            habitList.removeAt(position)
            notifyItemRemoved(position)
            true
        }

        /*LISTEN WHEN CHECKBOX IS CHECKED*/
        val checkBox : CheckBox = habitLayout.findViewById(R.id.habit_cb_completion)
        val habitName : TextView = habitLayout.findViewById(R.id.habit_tv_name)

        checkBox.setOnClickListener{
            val index = getHabitIndex(habitList, habitName.text.toString())
            val newHabit : Habit = habitList.get(index)
            newHabit.isCompleted =  true

            val dbo = DatabaseOperations(parent.context)
            dbo.updateItem(dbo, habitList.get(index), newHabit)

            dbo.close()
        }


        return ViewHolder(habitLayout)
    }


    //bind the view
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

    /* GET INDEX OF HABIT IN THE LIST */
    fun getHabitIndex(array: ArrayList<Habit>, habitName: String) : Int {
        var count = 0
        for (i in array){
            if (i.name.equals(habitName)) {
                return count
            }
            count++
        }
        return -1
    }
}