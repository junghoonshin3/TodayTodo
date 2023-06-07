package kr.sjh.data

import kr.sjh.data.entity.TodoEntity
import kr.sjh.domain.model.Todo

fun List<TodoEntity>.toTodoList(): List<Todo> {
    return this.map {
        Todo(it.id, it.date, it.title, it.today, it.is_check)
    }
}

fun List<Todo>.toTodoEntityList(): List<TodoEntity> {
    return this.map {
        TodoEntity(it.id, it.date, it.title, it.today, it.is_check)
    }
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(id, date, title, today, is_check)
}