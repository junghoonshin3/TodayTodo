package kr.sjh.list

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kr.sjh.domain.model.Item
import kr.sjh.domain.model.ListViewType
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.*
import kr.sjh.list.listener.ItemClickListener
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

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

    private val _todoList = MutableStateFlow<List<Item>>(emptyList())

    val todoList: StateFlow<List<Item>> = _todoList

    private val _openAdd = MutableSharedFlow<Boolean>()

    val openAdd = _openAdd.asSharedFlow()

    private val _todo = MutableSharedFlow<Todo>()

    val todo: SharedFlow<Todo> = _todo.asSharedFlow()

    init {
        viewModelScope.launch {
            getAllTodoList(DateTime.now().toDate())
        }
    }

    private suspend fun getAllTodoList(date: Date) {

        coroutineScope {

            val today = withContext(Dispatchers.IO) {

                getAllTodoListUseCase.invoke(
                    true,
                    date.time
                ).map {
                    it.viewType = ListViewType.ITEM
                    it
                }.toMutableList()
            }

            val tomorrow = withContext(Dispatchers.IO) {
                val c = Calendar.getInstance()
                c.time = date
                c.add(Calendar.DATE, 1)
                getAllTodoListUseCase.invoke(
                    false,
                    date.time
                ).map {
                    it.viewType = ListViewType.ITEM_TOMORROW
                    it
                }.toMutableList()
            }

            //today 헤더추가
            today.add(
            
            )

            //tomorrow 헤더추가
            tomorrow.add(
                0,
                Todo(
                    date = DateTime(),
                    title = "",
                    is_check = false,
                    today = false,
                    viewType = ListViewType.HEADER_TOMMOROW
                )
            )

            //아이템이 없는경우 뷰타입 EMPTY
            if (today.size <= 1) {
                today.add(
                    Todo(
                        date = DateTime(),
                        title = "",
                        is_check = false,
                        today = false,
                        viewType = ListViewType.EMPTY
                    )
                )
            }

            if (tomorrow.size <= 1) {
                tomorrow.add(
                    Todo(
                        date = DateTime(),
                        title = "",
                        is_check = false,
                        today = false,
                        viewType = ListViewType.EMPTY
                    )
                )
            }


            //헤더 추가후 배열 addAll
            today.addAll(tomorrow)

            _todoList.value = today

        }
    }

    fun show(v: View) {
        viewModelScope.launch {
            _openAdd.emit(true)
        }
    }

    fun close(v: View) {
        viewModelScope.launch {
            _openAdd.emit(false)
        }
    }

    fun update() {
        viewModelScope.launch {
            getAllTodoList(Date())
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