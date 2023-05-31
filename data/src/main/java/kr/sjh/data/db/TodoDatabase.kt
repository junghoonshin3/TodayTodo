package kr.sjh.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import kr.sjh.data.entity.TodoEntity

@Database([TodoEntity::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    companion object {
        const val APP_NAME = "todoDatabase.db"
    }


}