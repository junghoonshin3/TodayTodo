package kr.sjh.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.entity.TodoEntity
import org.joda.time.DateTime

interface LocalDataSource {
    fun getAllDailyTodoListByFlow(date: Long): Flow<List<TodoEntity>>

    suspend fun insertAllTodo(todoList: List<TodoEntity>)

    suspend fun insertTodo(todo: TodoEntity)

    suspend fun deleteTodo(id: Int)

    fun getAllTodoList(date: Long): Flow<List<TodoEntity>>

    suspend fun updateTodo(todo: TodoEntity): Int
}