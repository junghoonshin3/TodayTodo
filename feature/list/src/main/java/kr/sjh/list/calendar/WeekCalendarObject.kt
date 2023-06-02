package kr.sjh.list.calendar

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.view.WeekDayBinder
import kr.sjh.list.R
import kr.sjh.list.databinding.FragmentListBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class WeekCalendarObject(
    private val context: Context,
    private val binding: FragmentListBinding,
    private val dateClickListener: DateClickListener
) {
    private val weekCalendarView get() = binding.calendarView
    private var selectedDates = mutableListOf<LocalDate>()
    private val today = LocalDate.now()

    fun initWeekCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>
    ) {
        weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewContainer> {
            override fun create(view: View): WeekDayViewContainer =
                WeekDayViewContainer(view, ::dateClick)

            override fun bind(container: WeekDayViewContainer, data: WeekDay) {
                container.day = data
                bindDate(data.date, container.textView)
            }
        }

        weekCalendarView.setup(
            startMonth.atStartOfMonth(),
            endMonth.atEndOfMonth(),
            daysOfWeek.first(),
        )

        weekCalendarView.weekScrollListener = {
            updateTitle()
        }
        weekCalendarView.scrollToWeek(currentMonth.atStartOfMonth())
    }

    private fun bindDate(date: LocalDate, textView: TextView) {
        textView.text = date.dayOfMonth.toString()


//        if (isSelectable) {
//        when {
//            selectedDates.contains(date) -> {
//                textView.setTextColorRes(R.color.example_1_orange)
////                    selectedDates.clear()
//            }
//            today == date -> {
//                textView.setTextColorRes(R.color.example_1_orange)
//
////                    textView.setBackgroundResource(R.drawable.today_bg_1)
//            }
//            else -> {
//                textView.setTextColorRes(R.color.example_1_black)
////                    textView.background = null
//            }
//        }
//        } else {
//        textView.setTextColorRes(R.color.example_1_white)
//            textView.background = null
//        }

    }


    private fun updateTitle() {
        val week = weekCalendarView.findFirstVisibleWeek() ?: return
        val firstDate = week.days.first().date
        binding.tvCalendarYear.text = firstDate.year.toString()
        binding.tvCalendarMonth.text = firstDate.month.toString()
//        dateClick(firstDate)

    }

    private fun dateClick(date: LocalDate, textView: TextView) {

        if (selectedDates.contains(date)) {
            selectedDates.remove(date)
        } else {
            selectedDates.add(date)
        }
        weekCalendarView.notifyDateChanged(date)
        dateClickListener.onDateClick(date)
    }


}


fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColor(color))