package kr.sjh.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Int,
    val title: String,
    val today: Boolean,
    val is_check: Boolean,
)