package kr.sjh.domain.usecase.list

import kotlinx.coroutines.flow.Flow
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import javax.inject.Inject

class GetAllTodoListUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(today: Boolean, date: Int): List<Todo> {
        return repository.getAllTodoList(today, date)
    }
}