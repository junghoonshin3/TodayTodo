package kr.sjh.data.datasource.local

import kotlinx.coroutines.flow.Flow
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton

interface LocalDataSource {
    fun getAllDailyTodoListByFlow(date: Int): Flow<List<TodoEntity>>
}