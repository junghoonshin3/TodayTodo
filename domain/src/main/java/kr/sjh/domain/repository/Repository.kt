package kr.sjh.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo

interface Repository {
    fun getAllDailyTodoListByFlow(date: Int): Flow<List<Todo>>

    suspend fun insertAllTodo(list: List<Todo>)

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(id: Int)

    suspend fun getAllTodoList(today: Boolean, date: Int): List<Todo>


}