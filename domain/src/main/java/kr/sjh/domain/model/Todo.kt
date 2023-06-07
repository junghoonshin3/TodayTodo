package kr.sjh.domain.model

import java.util.*

data class Todo(
    val id: Int = 0,
    val date: Int,
    val title: String,
    val today: Boolean,
    val is_check: Boolean,
    var viewType: Int = ListViewType.ITEM
)