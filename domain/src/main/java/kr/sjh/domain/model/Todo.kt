package kr.sjh.domain.model

data class Todo(
    val id: Int = 0,
    val date: Int,
    val alaram: Boolean,
    val title: String,
    val when_do: String = "",
    val category: String = "",
    val emphasis: Boolean
)