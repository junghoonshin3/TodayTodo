package kr.sjh.domain.model

import org.joda.time.DateTime

data class Todo(
    val id: Int = 0,
    val date: DateTime,
    val title: String,
    val today: Boolean,
    var is_check: Boolean = false,
    var viewType: Int = if (today) ListViewType.ITEM else ListViewType.ITEM_TOMORROW
)