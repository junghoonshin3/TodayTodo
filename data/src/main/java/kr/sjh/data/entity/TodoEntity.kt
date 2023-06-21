package kr.sjh.data.entity

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Entity
@Parcelize
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: DateTime,
    val title: String,
    val today: Boolean,
    val is_check: Boolean,
) : Parcelable {
}