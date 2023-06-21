package kr.sjh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.sjh.data.datasource.local.LocalDataSource
import kr.sjh.data.toTodoEntity
import kr.sjh.data.toTodoEntityList
import kr.sjh.data.toTodoList
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TodoRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    Repository {

    override fun getAllDailyTodoListByFlow(date: Long): Flow<List<Todo>> {
        return localDataSource.getAllDailyTodoListByFlow(date).map {
            it.toTodoList()
        }
    }

    override suspend fun insertAllTodo(list: List<Todo>) {
        localDataSource.insertAllTodo(list.toTodoEntityList())
    }

    override suspend fun insertTodo(todo: Todo) {
        localDataSource.insertTodo(todo.toTodoEntity())
    }

    override suspend fun deleteTodo(id: Int) {
        localDataSource.deleteTodo(id)
    }


    override suspend fun getAllTodoList(today: Boolean, date: Long): List<Todo> {
        return localDataSource.getAllTodoList(today, date).map {
            Todo(it.id, it.date, it.title, it.today, it.is_check)
        }
    }

    override suspend fun updateTodo(todo: Todo): Int {
        return localDataSource.updateTodo(todo.toTodoEntity())
    }


}