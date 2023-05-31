package kr.sjh.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.entity.TodoEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodo(todoEntityList: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM TodoEntity WHERE date = :date")
    fun getAllDailyTodoListByFlow(date: Int): Flow<List<TodoEntity>>

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    suspend fun deleteTodo(id: Int)

}