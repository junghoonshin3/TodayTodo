package kr.sjh.list.dialog

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.InsertTodoUseCase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val insertTodoUseCase: InsertTodoUseCase
) : ViewModel() {


    private val _save = MutableSharedFlow<Boolean>()

    val save = _save.asSharedFlow()

    private val _isHourOpen = MutableSharedFlow<Boolean>()

    val isHourOpen = _isHourOpen.asSharedFlow()

    val _name = MutableStateFlow("")

    var _today = MutableStateFlow(true)

    private val cal = Calendar.getInstance()

    var _originHour = MutableStateFlow(cal.time)

    var _hour = MutableStateFlow(cal.time)

    fun save(v: View) {
        viewModelScope.launch {
            Log.i("sjh", "_name ${_name.value}")
            insertTodoUseCase.invoke(
                Todo(
                    date = cal.time,
                    title = _name.value,
                    today = _today.value,
                    hour = getDateToHour(_hour.value)
                )
            )
            _save.emit(true)
        }
    }

    fun today(compoundButton: CompoundButton, isChecked: Boolean) {
        if (!isChecked) {
            cal.add(Calendar.DATE, 1)
        } else {
            cal.time = Date()
        }
        _today.value = isChecked
    }

    fun isOpenTimePicker(v: View) {
        viewModelScope.launch {
            //시간저장
            _originHour.value = cal.time
            _isHourOpen.emit(true)
        }
    }

    fun isCloseTimePicker(v: View) {
        viewModelScope.launch {
            _hour.value = _originHour.value
            _isHourOpen.emit(false)
        }
    }

    fun confirmTimePicker(v: View) {
        viewModelScope.launch {
            _hour.emit(cal.time)
            _isHourOpen.emit(false)
        }
    }

    fun hour(v: TimePicker, hourOfDay: Int, minute: Int) {
        viewModelScope.launch {
            cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal.set(Calendar.MINUTE, minute)

        }
    }

    fun getDateToHour(date: Date): String {
        return SimpleDateFormat("HH:mm").format(date)
    }

}