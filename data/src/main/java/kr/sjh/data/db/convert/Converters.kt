package kr.sjh.data.db.convert

import android.util.Log
import androidx.room.TypeConverter
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return value?.let {
            DateTime(value)
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: DateTime?): Long? {
        return date?.millis
    }
}