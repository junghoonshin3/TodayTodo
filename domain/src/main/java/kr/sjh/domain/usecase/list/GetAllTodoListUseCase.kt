package kr.sjh.domain.usecase.list

import kr.sjh.domain.model.Item
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import java.util.*
import javax.inject.Inject

class GetAllTodoListUseCase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(today: Boolean, date:Long): List<Item> {

        return repository.getAllTodoList(today, date)
    }
}