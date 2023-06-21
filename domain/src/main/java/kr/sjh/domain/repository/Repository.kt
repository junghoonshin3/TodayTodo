package kr.sjh.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Item
import kr.sjh.domain.model.Todo
import java.util.*

interface Repository {
    fun getAllDailyTodoListByFlow(date: Long): Flow<List<Item>>

    suspend fun insertAllTodo(list: List<Item>)

    suspend fun insertTodo(todo: Item)

    suspend fun deleteTodo(id: Int)

    suspend fun getAllTodoList(today: Boolean, date: Long): List<Item>

    suspend fun updateTodo(todo: Todo): Int

}