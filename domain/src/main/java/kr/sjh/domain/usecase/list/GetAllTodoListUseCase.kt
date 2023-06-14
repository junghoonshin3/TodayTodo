package kr.sjh.domain.usecase.list

import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import java.util.*
import javax.inject.Inject

class GetAllTodoListUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(today: Boolean, date: Date): List<Todo> {

        return repository.getAllTodoList(today, date)
    }
}