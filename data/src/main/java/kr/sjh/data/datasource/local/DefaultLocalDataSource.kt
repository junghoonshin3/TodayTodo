package kr.sjh.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLocalDataSource @Inject constructor(
    private val todoDao: TodoDao
) : LocalDataSource {
    override fun getAllDailyTodoListByFlow(date: Date): Flow<List<TodoEntity>> =
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

    override suspend fun getAllTodoList(today: Boolean, date: Date): List<TodoEntity> {
        return todoDao.getAllTodoList(today, date)
    }

}