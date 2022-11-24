package com.example.deborahrimei_3015579_trackingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitMainAdapter(private val habitList: ArrayList<Habit>, val activity: MainActivity) :
    RecyclerView.Adapter<HabitMainAdapter.ViewHolder>() {

    //create class ViewHolder
    class ViewHolder(val habitlayout: RelativeLayout) : RecyclerView.ViewHolder(habitlayout) {
    }


    // this fun will return the View in the activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val relativeLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_layout, parent, false) as RelativeLayout

        return ViewHolder(relativeLayout)
    }


    //bind the view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val habitBindLayout = holder.habitlayout

        val nameTextView = habitBindLayout.getChildAt(0) as TextView
        val dateTextView = habitBindLayout.getChildAt(1) as TextView
        val completionCheckBox = habitBindLayout.getChildAt(2) as CheckBox

        nameTextView.text = habitList[position].name
        dateTextView.text = habitList[position].getDateAsString()

    }

    override fun getItemCount(): Int {
        return habitList.size
    }
}