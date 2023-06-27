package kr.sjh.data.db.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.sjh.data.entity.TodoEntity
import java.util.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTodo(todoEntityList: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM TodoEntity WHERE id = :id")
    suspend fun deleteTodo(id: Int)

    @Query("SELECT * FROM TodoEntity WHERE date >= :date")
    fun getAllTodoList(date: Long): List<TodoEntity>

    @Update
    suspend fun updateTodo(todo: TodoEntity): Int

}