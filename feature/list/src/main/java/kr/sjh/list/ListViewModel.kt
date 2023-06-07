package kr.sjh.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.sjh.domain.model.ListViewType
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.*
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

    val todoList: LiveData<List<Todo>> get() = _todoList

    private var _todoList: MutableLiveData<List<Todo>> = MutableLiveData()

    init {
//        initDataTest()
        viewModelScope.launch {
            getAllTodoList(20230606)
        }
    }

    private fun initDataTest() {
        viewModelScope.launch {
            //mock data
            insertAllTodoUseCase.invoke(
                listOf(
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = true,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = false,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = false,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = false,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = false,
                        is_check = false,
                    ),
                    Todo(
                        date = 20230606,
                        title = "영화 예매하기",
                        today = false,
                        is_check = false,
                    ),
                )
            )
        }
    }

    private suspend fun getAllTodoList(date: Int) {
        coroutineScope {
            val today = withContext(Dispatchers.IO) {
                getAllTodoListUseCase.invoke(
                    true,
                    date
                ).toMutableList()
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

            //오늘 헤더추가
            today.add(
                0,
                Todo(
                    date = 20220505,
                    title = "",
                    is_check = false,
                    today = false,
                    viewType = ListViewType.HEADER_TODAY
                )
            )
            //내일 헤더추가
            tomorrow.add(
                0,
                Todo(
                    date = 20220505,
                    title = "",
                    is_check = false,
                    today = false,
                    viewType = ListViewType.HEADER_TOMMOROW
                )
            )

            today.addAll(tomorrow)
            Log.i("sjh", "today : ${today.size}")
            _todoList.value = today
        }
    }
}