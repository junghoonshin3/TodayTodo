package kr.sjh.data.db.convert

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let {
            SimpleDateFormat("yyyyMMdd").parse(value)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return SimpleDateFormat("yyyyMMdd").format(date)
    }
}