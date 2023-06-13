package kr.sjh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.sjh.data.db.convert.Converters
import kr.sjh.data.db.dao.TodoDao
import kr.sjh.data.entity.TodoEntity

@Database([TodoEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        const val APP_NAME = "todoDatabase.db"
    }

    abstract fun todoDao(): TodoDao


}