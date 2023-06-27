package kr.sjh.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kr.sjh.data.datasource.local.LocalDataSource
import kr.sjh.data.toTodoEntity
import kr.sjh.data.toTodoEntityList
import kr.sjh.data.toTodoList
import kr.sjh.domain.model.ListViewType
import kr.sjh.domain.model.Todo
import kr.sjh.domain.repository.Repository
import org.joda.time.DateTime
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TodoRepository @Inject constructor(private val localDataSource: LocalDataSource) :
    Repository {

    override suspend fun insertAllTodo(list: List<Todo>) {
        localDataSource.insertAllTodo(list.toTodoEntityList())
    }

    override suspend fun insertTodo(todo: Todo) {
        localDataSource.insertTodo(todo.toTodoEntity())
    }

    override suspend fun deleteTodo(id: Int) {
        localDataSource.deleteTodo(id)
    }


    override fun getAllTodoList(date: Long): Flow<List<Todo>> {
        return localDataSource.getAllTodoList(date).map {
            val list = it.toTodoList().toMutableList()

            val todayList = list.filter { it.today }.toMutableList()
            val tomorrowList = list.filter { !it.today }.toMutableList()

            if (todayList.isNotEmpty()) {
                todayList.sortByDescending {
                    it.date.millis
                }
                todayList.add(
                    0,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.HEADER_TODAY)
                )

            } else {
                todayList.add(
                    0,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.HEADER_TODAY)
                )
                todayList.add(
                    1,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.EMPTY)
                )
            }

            if (tomorrowList.isNotEmpty()) {
                tomorrowList.sortByDescending { it.date.millis }
                tomorrowList.add(
                    0,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.HEADER_TOMMOROW)
                )
            } else {
                tomorrowList.add(
                    0,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.HEADER_TOMMOROW)
                )
                tomorrowList.add(
                    1,
                    Todo(0, DateTime.now(), "", true, viewType = ListViewType.EMPTY)
                )
            }


            (todayList + tomorrowList).toList()
        }
    }

    override suspend fun updateTodo(todo: Todo): Int {
        return localDataSource.updateTodo(todo.toTodoEntity())
    }

}