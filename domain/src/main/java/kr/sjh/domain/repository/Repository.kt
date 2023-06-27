package kr.sjh.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo

interface Repository {

    suspend fun insertAllTodo(list: List<Todo>)

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(id: Int)

    fun getAllTodoList(date: Long): Flow<List<Todo>>

    suspend fun updateTodo(todo: Todo): Int

}