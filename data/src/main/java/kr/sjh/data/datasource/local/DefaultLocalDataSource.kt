package kr.sjh.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLocalDataSource @Inject constructor(
    private val todoDao: TodoDao
) : LocalDataSource {
    override fun getAllDailyTodoListByFlow(date: Int): Flow<List<TodoEntity>> =
        todoDao.getAllDailyTodoListByFlow(date)

}