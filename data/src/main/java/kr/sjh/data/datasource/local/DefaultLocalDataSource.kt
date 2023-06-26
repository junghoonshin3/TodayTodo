package kr.sjh.data.datasource.local

import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLocalDataSource @Inject constructor(
    private val todoDao: TodoDao
) : LocalDataSource {
    override fun getAllDailyTodoListByFlow(date: Long): Flow<List<TodoEntity>> =
        todoDao.getAllDailyTodoListByFlow(date)

    override suspend fun insertAllTodo(todoList: List<TodoEntity>) {
        todoDao.insertAllTodo(todoList)
    }

    override suspend fun insertTodo(todo: TodoEntity) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(id: Int) {
        todoDao.deleteTodo(id)
    }

    override fun getAllTodoList(date: Long): Flow<List<TodoEntity>> {
        return callbackFlow {
            val list = todoDao.getAllTodoList(date)
            if (list.isEmpty()) {
                send(emptyList())
            } else {
                send(list)
            }
            awaitClose()
        }
    }

    override suspend fun updateTodo(todo: TodoEntity): Int {
        return todoDao.updateTodo(todo)
    }

}