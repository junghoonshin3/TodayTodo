package kr.sjh.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.list.DeleteTodoUseCase
import kr.sjh.domain.usecase.list.GetTodoListUseCase
import kr.sjh.domain.usecase.list.InsertAllTodoUseCase
import kr.sjh.domain.usecase.list.InsertTodoUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val insertAllTodoUseCase: InsertAllTodoUseCase,
    private val insertTodoUseCase: InsertTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) :
    ViewModel() {


    val todoList: LiveData<List<Todo>> = getTodoListUseCase(20230602).asLiveData()

    fun initDataTest() {
        viewModelScope.launch {
            //mock data
            insertAllTodoUseCase.invoke(
                listOf(
                    Todo(
                        date = 20230602,
                        alaram = false,
                        title = "친구들과 여행가기",
                        when_do = "20230605",
                        category = "힐링",
                        emphasis = true
                    ),
                    Todo(
                        date = 20230602,
                        alaram = false,
                        title = "매일 코딩하기",
                        when_do = "20230605",
                        category = "루틴",
                        emphasis = true
                    ),
                    Todo(
                        date = 20230602,
                        alaram = false,
                        title = "여자친구 만나기",
                        when_do = "20230605",
                        category = "데이트",
                        emphasis = true
                    ),
                    Todo(
                        date = 20230602,
                        alaram = false,
                        title = "이력서 쓰고 면접보러가기",
                        when_do = "20230605",
                        category = "개인일정",
                        emphasis = true
                    )
                )
            )
        }
        }
    }