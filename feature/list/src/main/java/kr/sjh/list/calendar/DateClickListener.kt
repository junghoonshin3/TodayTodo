package kr.sjh.list.calendar

import android.widget.TextView
import java.time.LocalDate

interface DateClickListener {
    fun onDateClick(date : LocalDate)
}