package kr.sjh.list

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getTodoListUseCase: GetAllDailyTodoListUseCase,
    private val insertAllTodoUseCase: InsertAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val getAllTodoListUseCase: GetAllTodoListUseCase
) :
    ViewModel() {

    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())

    val todoList: StateFlow<List<Todo>> = _todoList

    private val _openAdd = MutableSharedFlow<Boolean>()

    val openAdd = _openAdd.asSharedFlow()


    init {
        viewModelScope.launch {
            getAllTodoList(Date())
        }
    }

    private suspend fun getAllTodoList(date: Date) {
        coroutineScope {
            val today = withContext(Dispatchers.IO) {
                getAllTodoListUseCase.invoke(
                    true,
                    date
                ).map {
                    it.viewType = ListViewType.ITEM
                    it
                }.toMutableList()
            }

            val tomorrow = withContext(Dispatchers.IO) {
                getAllTodoListUseCase.invoke(
                    false,
                    date
                ).map {
                    it.viewType = ListViewType.ITEM_TOMORROW
                    it
                }.toMutableList()
            }

            //today 헤더추가
            today.add(
                0,
                Todo(
                    date = Date(),
                    title = "",
                    is_check = false,
                    today = false,
                    time = "",
                    viewType = ListViewType.HEADER_TODAY
                )
            )

            //tomorrow 헤더추가
            tomorrow.add(
                0,
                Todo(
                    date = Date(),
                    title = "",
                    is_check = false,
                    today = false,
                    time = "",
                    viewType = ListViewType.HEADER_TOMMOROW
                )
            )

            //아이템이 없는경우 뷰타입 EMPTY
            if (today.size <= 1) {
                today.add(
                    Todo(
                        date = Date(),
                        title = "",
                        is_check = false,
                        time = "",
                        today = false,
                        viewType = ListViewType.EMPTY
                    )
                )
            }

            if (tomorrow.size <= 1) {
                tomorrow.add(
                    Todo(
                        date = Date(),
                        title = "",
                        is_check = false,
                        today = false,
                        time = "",
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
}