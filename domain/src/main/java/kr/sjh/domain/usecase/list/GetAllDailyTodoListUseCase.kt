package kr.sjh.domain.usecase.list

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import java.util.*
import javax.inject.Inject

class GetAllDailyTodoListUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(date: Date): Flow<List<Todo>> {
        return repository.getAllDailyTodoListByFlow(date)
    }
}