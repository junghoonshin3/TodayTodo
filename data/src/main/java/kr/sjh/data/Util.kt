package kr.sjh.data

import kr.sjh.data.entity.TodoEntity
import kr.sjh.domain.model.Todo

fun List<TodoEntity>.toTodoList(): List<Todo> {
    return this.map {
        Todo(it.id, it.date, it.alaram, it.title, it.when_do, it.category, it.emphasis)
    }
}