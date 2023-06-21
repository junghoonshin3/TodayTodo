package kr.sjh.domain.model

import org.joda.time.DateTime

data class Todo(
    val date: DateTime,
    val title: String,
    val today: Boolean,
    var is_check: Boolean = false
) : Item()