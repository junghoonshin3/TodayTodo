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

    var _today = MutableStateFlow(false)

    var _hour = MutableStateFlow(Date())


    private val cal = Calendar.getInstance()


    private val _time = MutableStateFlow(getDateToHour())

    val time = _time.asStateFlow()


    fun save(v: View) {
        viewModelScope.launch {
            Log.i("sjh", "_name ${_name.value}")
            insertTodoUseCase.invoke(
                Todo(
                    date = _hour.value,
                    title = _name.value,
                    today = _today.value,
                    time = _time.value
                )
            )
            _isHourOpen.emit(false)
        }
    }

    fun today(compoundButton: CompoundButton, isChecked: Boolean) {
        _hour.value = cal.time
        _today.value = isChecked
    }

    fun isOpenTimePicker(v: View) {
        viewModelScope.launch {
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
            _time.emit(getDateToHour())
            _isHourOpen.emit(false)
        }
    }

    fun hour(v: TimePicker, hourOfDay: Int, minute: Int) {
        viewModelScope.launch {
            
        }
    }

    //
    fun getDateToHour(): String {
        return SimpleDateFormat("HH:mm").format(_hour.value)
    }

}