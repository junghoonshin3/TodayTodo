package kr.sjh.list.calendar

import android.icu.util.LocaleData
import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.view.ViewContainer
import kr.sjh.list.databinding.CalendarDayLayoutBinding
import java.time.LocalDate

class WeekDayViewContainer(view: View, private val callback: (LocalDate, TextView) -> Unit) :
    ViewContainer(view) {
    lateinit var day: WeekDay
    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

    init {
        view.setOnClickListener {
            if (day.position == WeekDayPosition.RangeDate) {
                callback(day.date, textView)
            }
        }
    }
}