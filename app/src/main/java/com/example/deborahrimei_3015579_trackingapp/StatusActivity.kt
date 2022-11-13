package com.example.deborahrimei_3015579_trackingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class StatusActivity : AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        _textview = findViewById(R.id.calendar_textView)
        _calendarview = findViewById(R.id.calendarView)

        //show current date as text view
        _textview?.setText(SimpleDateFormat("dd MMM yyyy").format(Date()))

        //change text view depending on what has been selected in the calendar
        _calendarview?.setOnDateChangeListener { view, year, month, day ->
            val month_name = SimpleDateFormat("MMM").format(Date(year, month,day))
            _textview?.setText("$day $month_name $year")
        }

    }

    private var _textview: TextView? = null
    private var _calendarview: CalendarView? = null
}