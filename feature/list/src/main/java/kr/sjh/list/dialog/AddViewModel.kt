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

    private val _dateTime = MutableStateFlow(DateTime.now())

    val dateTime = _dateTime.asStateFlow()

    var tempHour = 0

    var tempMinute = 0


    fun confirmTimePicker(v: View) {
        viewModelScope.launch {
            _dateTime.emit(
                _dateTime.value.withHourOfDay(tempHour).withMinuteOfHour(tempMinute)
            )
            _timePickerOpen.emit(false)
        }
    }

    fun changeToday(isChecked: Boolean) {
        viewModelScope.launch {
            if (!isChecked) {
                _dateTime.emit(_dateTime.value.plusDays(1))
            } else {
                //오늘날짜
                _dateTime.emit(currentDateTime(tempHour, tempMinute))
            }
        }
    }

    private fun currentDateTime(tempHour: Int, tempMinute: Int) =
        DateTime.now().withHourOfDay(tempHour).withMinuteOfHour(tempMinute)

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