package kr.sjh.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.sjh.data.datasource.local.LocalDataSource
import kr.sjh.data.toTodoEntity
import kr.sjh.data.toTodoEntityList
import kr.sjh.data.toTodoList
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TodoRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    Repository {

    override fun getAllDailyTodoListByFlow(date: Int): Flow<List<Todo>> {
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


}