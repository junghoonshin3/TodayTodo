package kr.sjh.list.dialog

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.InsertTodoUseCase
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
) : ViewModel() {


    private val _timePickerOpen = MutableSharedFlow<Boolean>()

    val timePickerOpen = _timePickerOpen.asSharedFlow()

    val _name = MutableStateFlow("")

    var _today = MutableStateFlow(true)

    var dateTime = DateTime.now()

    private val _hourOfDay = MutableStateFlow(dateTime.hourOfDay().get())

    val hourOfDay = _hourOfDay.asStateFlow()

    private val _minute = MutableStateFlow(dateTime.minuteOfHour().get())

    val minute = _minute.asStateFlow()

    var tempHour = 0

    var tempMinute = 0


    fun confirmTimePicker(v: View) {
        viewModelScope.launch {
            _hourOfDay.emit(tempHour)
            _minute.emit(tempMinute)
            dateTime.apply {
                withHourOfDay(this@AddViewModel.hourOfDay.value)
                withMinuteOfHour(minute.value)
//                if (!_today.value) minusDays(1)
//                else plusDays(1)
            }
            _timePickerOpen.emit(false)
        }
    }

    fun cancleTimePicker(v: View) {
        viewModelScope.launch {
            _timePickerOpen.emit(false)
        }
    }

    fun hour(v: TimePicker, hourOfDay: Int, minute: Int) {
        tempHour = hourOfDay
        tempMinute = minute
    }

    fun timePickerOpen(v: View) {
        viewModelScope.launch {
            //타임피커 오픈
            Log.i("sjh", "timePickerOpen")
            _timePickerOpen.emit(true)
        }
    }

}