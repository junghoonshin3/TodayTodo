package kr.sjh.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.entity.TodoEntity
import java.util.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodo(todoEntityList: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM TodoEntity WHERE date = :date")
    fun getAllDailyTodoListByFlow(date: Date): Flow<List<TodoEntity>>

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("SELECT * FROM TodoEntity WHERE today = :today AND date = :date")
    suspend fun getAllTodoList(today: Boolean, date: Date): List<TodoEntity>

}