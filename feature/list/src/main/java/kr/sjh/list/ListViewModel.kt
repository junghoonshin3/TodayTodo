package kr.sjh.list

import android.os.Build
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kr.sjh.domain.model.ListViewType
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.*
import kr.sjh.list.listener.ItemClickListener
import org.joda.time.DateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ListViewModel @Inject constructor(
    private val insertAllTodoUseCase: InsertAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getAllTodoListUseCase: GetAllTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) :
    ViewModel(), ItemClickListener {

    val today = Calendar.getInstance().run {
        set(Calendar.MILLISECOND, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        time
    }

    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())

    val todoList: StateFlow<List<Todo>> = _todoList.asStateFlow()

    val _openAdd = MutableSharedFlow<Boolean>()

    val openAdd = _openAdd.asSharedFlow()

    private val _todo = MutableSharedFlow<Todo>()

    val todo: SharedFlow<Todo> = _todo.asSharedFlow()

    private val _isEmptyName = MutableSharedFlow<Boolean>()

    val isEmptyName = _isEmptyName.asSharedFlow()

    init {
        getAllTodoList(today.time)
    }

    fun getAllTodoList(date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTodoListUseCase.invoke(date).distinctUntilChanged().collect {
                _todoList.value = it
            }
        }
    }

    fun save(v: View, name: String, today: Boolean, date: DateTime) {
        viewModelScope.launch {
            if (name.isEmpty()) {
                _isEmptyName.emit(true)
                return@launch
            }
            insertTodoUseCase.invoke(
                Todo(
                    date = date,
                    title = name,
                    today = today,
                )
            )

            getAllTodoList(this@ListViewModel.today.time)

            dismiss()
        }
    }

    fun show(v: View) {
        viewModelScope.launch {
            _openAdd.emit(true)
        }
    }

    fun dismiss() {
        viewModelScope.launch {
            _openAdd.emit(false)
        }
    }

    override fun onItemClick(todo: Todo) {
        viewModelScope.launch {
            _todo.emit(todo)

        }
    }

    override fun onCheckBoxClick(todo: Todo, isCheck: Boolean) {
        viewModelScope.launch {
            updateTodoUseCase.invoke(todo)
        }
    }


}