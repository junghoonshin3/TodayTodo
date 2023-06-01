package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.WeekDayPosition
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekCalendarView
import com.kizitonwose.calendar.view.WeekDayBinder
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.list.databinding.CalendarDayLayoutBinding
import kr.sjh.list.databinding.FragmentListBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class ListFragment : Fragment() {
    companion object {
        fun newInstance() = ListFragment()
    }

    private val list: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding
    private val weekCalendarView: WeekCalendarView get() = binding.calendarView
    private val selectedDates = mutableSetOf<LocalDate>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        val daysOfWeek = daysOfWeek()

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        setupWeekCalendar(startMonth, endMonth, currentMonth, daysOfWeek)


        list.todoList.observe(viewLifecycleOwner) {
            Log.i("sjh", "${it.size}")
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupWeekCalendar(
        startMonth: YearMonth,
        endMonth: YearMonth,
        currentMonth: YearMonth,
        daysOfWeek: List<DayOfWeek>,
    ) {
        class WeekDayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: WeekDay
            val textView = CalendarDayLayoutBinding.bind(view).calendarDayText

            init {
                view.setOnClickListener {
                    if (day.position == WeekDayPosition.RangeDate) {
//                        dateClicked(date = day.date)
                    }
                }
            }
        }
        weekCalendarView.dayBinder = object : WeekDayBinder<WeekDayViewContainer> {
            override fun create(view: View): WeekDayViewContainer = WeekDayViewContainer(view)
            override fun bind(container: WeekDayViewContainer, data: WeekDay) {
                container.day = data
                bindDate(data.date, container.textView, data.position == WeekDayPosition.RangeDate)
            }
        }
//        weekCalendarView.weekScrollListener = { updateTitle() }
        weekCalendarView.setup(
            startMonth.atStartOfMonth(),
            endMonth.atEndOfMonth(),
            daysOfWeek.first(),
        )
        weekCalendarView.scrollToWeek(currentMonth.atStartOfMonth())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindDate(date: LocalDate, textView: TextView, isSelectable: Boolean) {
        textView.text = date.dayOfMonth.toString()
//        if (isSelectable) {
//            when {
//                selectedDates.contains(date) -> {
////                    textView.setTextColorRes(R.color.example_1_bg)
////                    textView.setBackgroundResource(R.drawable.example_1_selected_bg)
//                }
//                today == date -> {
////                    textView.setTextColorRes(R.color.example_1_white)
////                    textView.setBackgroundResource(R.drawable.example_1_today_bg)
//                }
//                else -> {
//                    textView.setTextColorRes(R.color.example_1_white)
//                    textView.background = null
//                }
//            }
//        } else {
//            textView.setTextColorRes(R.color.example_1_white_light)
//            textView.background = null
//        }
    }
//
//    private fun dateClicked(date: LocalDate) {
//        if (selectedDates.contains(date)) {
//            selectedDates.remove(date)
//        } else {
//            selectedDates.add(date)
//        }
//        // Refresh both calendar views..
//        monthCalendarView.notifyDateChanged(date)
//        weekCalendarView.notifyDateChanged(date)
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun updateTitle() {
//        val isMonthMode = !binding.weekModeCheckBox.isChecked
//        if (isMonthMode) {
//            val month = monthCalendarView.findFirstVisibleMonth()?.yearMonth ?: return
//            binding.exOneYearText.text = month.year.toString()
//            binding.exOneMonthText.text = month.month.displayText(short = false)
//        } else {
//            val week = weekCalendarView.findFirstVisibleWeek() ?: return
//            // In week mode, we show the header a bit differently because
//            // an index can contain dates from different months/years.
//            val firstDate = week.days.first().date
//            val lastDate = week.days.last().date
//            if (firstDate.yearMonth == lastDate.yearMonth) {
//                binding.exOneYearText.text = firstDate.year.toString()
//                binding.exOneMonthText.text = firstDate.month.displayText(short = false)
//            } else {
//                binding.exOneMonthText.text =
//                    firstDate.month.displayText(short = false) + " - " +
//                            lastDate.month.displayText(short = false)
//                if (firstDate.year == lastDate.year) {
//                    binding.exOneYearText.text = firstDate.year.toString()
//                } else {
//                    binding.exOneYearText.text = "${firstDate.year} - ${lastDate.year}"
//                }
//            }
//        }
//    }

}