package kr.sjh.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(private val repository: Repository) {

    operator fun invoke(date: Int): Flow<List<Todo>> {
        return repository.getAllDailyTodoListByFlow(date)
    }

}