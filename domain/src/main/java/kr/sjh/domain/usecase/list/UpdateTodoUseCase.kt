package kr.sjh.domain.usecase.list

import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo)
    }
}