package kr.sjh.list.dialog

import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Todo
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor() : ViewModel() {


    private val _save = MutableSharedFlow<Boolean>()

    val save = _save.asSharedFlow()

    private val _hour = MutableSharedFlow<Boolean>()

    val hour = _hour.asSharedFlow()

    val _name = MutableStateFlow("")

    private val _today = MutableSharedFlow<Boolean>()

    val today = _today.asSharedFlow()


    fun save() {
        viewModelScope.launch {
            _save.emit(true)
        }
    }

    fun today(v: View) {
        viewModelScope.launch {
            _hour.emit(true)
        }
    }

    fun isOpenTimePicker(v: View) {
//        v.requestFocusFromTouch()
        viewModelScope.launch {
            _hour.emit(true)
        }
    }

    fun isCloseTimePicker(v: View) {
        viewModelScope.launch {
            _hour.emit(false)
        }
    }

}