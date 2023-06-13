package kr.sjh.data

import android.os.Build
import androidx.annotation.RequiresApi
import kr.sjh.data.entity.TodoEntity
import kr.sjh.domain.model.Todo

@RequiresApi(Build.VERSION_CODES.O)
fun List<TodoEntity>.toTodoList(): List<Todo> {
    return this.map {
        Todo(it.id, it.date, it.title, it.time, it.today, it.is_check)
    }
}

fun List<Todo>.toTodoEntityList(): List<TodoEntity> {
    return this.map {
        TodoEntity(it.id, it.date, it.title, it.time, it.today, it.is_check)
    }
}

fun Todo.toTodoEntity(): TodoEntity {
    return TodoEntity(id, date, title, time, today, is_check)
}