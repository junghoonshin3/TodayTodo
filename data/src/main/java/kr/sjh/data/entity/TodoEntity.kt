package kr.sjh.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Int,
    val alaram: Boolean,
    val title: String,
    val when_do: String = "",
    val category: String = "",
    val emphasis: Boolean
)