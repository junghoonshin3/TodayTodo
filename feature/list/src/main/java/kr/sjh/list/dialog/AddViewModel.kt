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

    var selectHour = 0

    var selectMinute = 0

    private val _hour = MutableStateFlow(cal.get(Calendar.HOUR_OF_DAY))

    val hour = _hour.asStateFlow()

    private val _minute = MutableStateFlow(cal.get(Calendar.MINUTE))

    val minute = _minute.asStateFlow()

    val _confirm = MutableStateFlow(false)

    private val _isEmptyName = MutableSharedFlow<Boolean>()

    val isEmptyName = _isEmptyName.asSharedFlow()

    fun save(v: View) {

        viewModelScope.launch {
            if (_name.value.isNullOrEmpty()) {
                _isEmptyName.emit(true)
                return@launch
            }
            insertTodoUseCase.invoke(
                Todo(
                    date = cal.time,
                    title = _name.value,
                    today = _today.value,
                    hour = _hour.value,
                    minute = _minute.value
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
            _isHourOpen.emit(true)
        }
    }

    fun isCloseTimePicker(v: View) {
        viewModelScope.launch {
            _isHourOpen.emit(false)
        }
    }

    fun confirmTimePicker(v: View) {
        viewModelScope.launch {
            _hour.emit(selectHour)
            _minute.emit(selectMinute)
            _isHourOpen.emit(false)
            _confirm.emit(true)
        }
    }


    fun hour(v: TimePicker, hourOfDay: Int, minute: Int) {
        viewModelScope.launch {
            selectHour = hourOfDay
            selectMinute = minute
        }
    }

}