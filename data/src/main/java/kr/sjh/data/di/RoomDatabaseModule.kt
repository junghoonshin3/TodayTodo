package kr.sjh.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.sjh.data.db.TodoDatabase
import kr.sjh.data.db.convert.Converters
import kr.sjh.data.db.dao.TodoDao
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, TodoDatabase.APP_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao()
    }


}