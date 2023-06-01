package kr.sjh.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo

interface Repository {
    fun getAllDailyTodoListByFlow(date: Int): Flow<List<Todo>>
}