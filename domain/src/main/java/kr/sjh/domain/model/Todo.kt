package kr.sjh.domain.model

import java.util.*

data class Todo(
    val id: Int = 0,
    val date: Date,
    val hour: Int,
    val minute: Int,
    val title: String,
    val today: Boolean,
    val is_check: Boolean = false,
    var viewType: Int = ListViewType.ITEM
)