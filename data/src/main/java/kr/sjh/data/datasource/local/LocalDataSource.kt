package kr.sjh.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

interface LocalDataSource {
    fun getAllDailyTodoListByFlow(date: Date): Flow<List<TodoEntity>>

    suspend fun insertAllTodo(todoList: List<TodoEntity>)

    suspend fun insertTodo(todo: TodoEntity)

    suspend fun deleteTodo(id: Int)

    suspend fun getAllTodoList(today: Boolean, date: Date): List<TodoEntity>
}