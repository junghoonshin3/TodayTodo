package kr.sjh.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo
import java.util.*

interface Repository {
    fun getAllDailyTodoListByFlow(date: Date): Flow<List<Todo>>

    suspend fun insertAllTodo(list: List<Todo>)

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(id: Int)

    suspend fun getAllTodoList(today: Boolean, date: Date): List<Todo>


}