package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.view.WeekCalendarView
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.list.calendar.DateClickListener
import kr.sjh.list.calendar.WeekCalendarObject
import kr.sjh.list.databinding.FragmentListBinding
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class ListFragment : Fragment(), DateClickListener {
    companion object {
        fun newInstance() = ListFragment()
    }

    private val list: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding
    private val weekCalendarView: WeekCalendarView get() = binding.calendarView
    private val selectedDates = mutableSetOf<LocalDate>()
    lateinit var weekCalendarObject: WeekCalendarObject


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

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val dayOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.SUNDAY)

        weekCalendarObject = WeekCalendarObject(requireContext(), binding, this)

        weekCalendarObject.initWeekCalendar(startMonth, endMonth, currentMonth, dayOfWeek)

        list.todoList.observe(viewLifecycleOwner) {
            Log.i("sjh", "${it.size}")
        }
    }

    override fun onDateClick(date: LocalDate) {

    }
}