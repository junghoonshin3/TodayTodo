package kr.sjh.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.sjh.domain.model.Todo
import kr.sjh.domain.usecase.GetTodoListUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getTodoListUseCase: GetTodoListUseCase) :
    ViewModel() {

    val todoList: LiveData<List<Todo>> = getTodoListUseCase(20230601).asLiveData()

}