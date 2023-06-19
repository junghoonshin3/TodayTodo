package kr.sjh.list

import android.util.Log
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dagger.Binds
import kr.sjh.domain.model.Todo
import java.text.SimpleDateFormat
import java.util.*

fun dateToString(todo: Todo): String {

    return SimpleDateFormat(
        "yyyy년 MM월 dd일  ${
            String.format(
                "%02d:%02d",
                todo.hour,
                todo.minute
            )
        }"
    ).format(todo.date)
}