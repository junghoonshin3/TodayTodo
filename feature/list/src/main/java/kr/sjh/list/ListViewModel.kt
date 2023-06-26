package kr.sjh.list

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    private val getTodoListUseCase: GetAllDailyTodoListUseCase,
    private val insertAllTodoUseCase: InsertAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getAllTodoListUseCase: GetAllTodoListUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) :
    ViewModel(), ItemClickListener {

    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())

    val today = Calendar.getInstance().run {
        set(Calendar.MILLISECOND, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        time
    }
    val todoList: StateFlow<List<Todo>> =
        getAllTodoListUseCase.invoke(today.time).flowOn(Dispatchers.IO).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000), initialValue = emptyList()
        )

    private val _openAdd = MutableSharedFlow<Boolean>()

    val openAdd = _openAdd.asSharedFlow()

    private val _todo = MutableSharedFlow<Todo>()

    val todo: SharedFlow<Todo> = _todo.asSharedFlow()

    private val _isEmptyName = MutableSharedFlow<Boolean>()

    val isEmptyName = _isEmptyName.asSharedFlow()

    init {
//        viewModelScope.launch {
//            val today = Calendar.getInstance()
//            today[Calendar.MILLISECOND] = 0
//            today[Calendar.SECOND] = 0
//            today[Calendar.MINUTE] = 0
//            today[Calendar.HOUR_OF_DAY] = 0
//        }
    }

    fun getAllTodoList(date: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllTodoListUseCase.invoke(date)
        }
    }

    fun save(v: View, name: String, today: Boolean) {
        viewModelScope.launch {
            if (name.isEmpty()) {
                _isEmptyName.emit(true)
                return@launch
            }
            var dateTime = DateTime.now()
            insertTodoUseCase.invoke(
                Todo(
                    date = dateTime,
                    title = name,
                    today = today,
                )
            )

            getAllTodoList(dateTime.toDate().time)

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

    override fun onItemClick(todo: Todo, isClick: Boolean) {
        viewModelScope.launch {
            todo.is_check = isClick
            if (isClick) {
                updateTodoUseCase.invoke(todo)
            }
        }

    }

}