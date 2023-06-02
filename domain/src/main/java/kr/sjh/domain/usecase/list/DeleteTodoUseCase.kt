package kr.sjh.domain.usecase.list

import kr.sjh.domain.repository.Repository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }
}