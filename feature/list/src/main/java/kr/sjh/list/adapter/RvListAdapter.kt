package kr.sjh.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.domain.model.ListViewType.EMPTY
import kr.sjh.domain.model.ListViewType.HEADER_TODAY
import kr.sjh.domain.model.ListViewType.HEADER_TOMMOROW
import kr.sjh.domain.model.ListViewType.ITEM
import kr.sjh.domain.model.ListViewType.ITEM_TOMORROW
import kr.sjh.domain.model.Todo
import kr.sjh.list.databinding.*
import kr.sjh.list.listener.ItemClickListener

class RvListAdapter : ListAdapter<Todo, RecyclerView.ViewHolder>(ItemDiffUtil), ItemClickListener {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_TODAY ->
                TodoListTodayHeaderViewHolder(
                    RecyclerviewTodoListTodayBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                )
            HEADER_TOMMOROW ->
                TodoListTomorrowHeaderViewHolder(
                    RecyclerviewTodoListTomorrowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM ->
                TodoListItemViewHolder(
                    RecyclerviewTodoListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            EMPTY -> // EMPTY
                TodoListEmptyHeaderViewHolder(
                    RecyclerviewTodoListEmptyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            ITEM_TOMORROW -> {
                TodoListTomorrowItemViewHolder(
                    RecyclerviewTodoListTomorrowItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                throw ClassCastException("Unknown viewType $viewType")
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoListTodayHeaderViewHolder -> {

            }
            is TodoListTomorrowHeaderViewHolder -> {}
            is TodoListItemViewHolder -> {
                holder.bind(getItem(position))
            }
            is TodoListTomorrowItemViewHolder -> {
                holder.bind(getItem(position))
            }
            is TodoListEmptyHeaderViewHolder -> {}
        }
    }

    override fun onItemClick(todo: Todo, isCheck: Boolean) {
        Log.i("sjh", "id : ${todo.id} isCheck : $isCheck")
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    inner class TodoListItemViewHolder(private val binding: RecyclerviewTodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
            binding.listener = this@RvListAdapter
        }
    }

    inner class TodoListTodayHeaderViewHolder(private val binding: RecyclerviewTodoListTodayBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class TodoListTomorrowHeaderViewHolder(private val binding: RecyclerviewTodoListTomorrowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class TodoListEmptyHeaderViewHolder(private val binding: RecyclerviewTodoListEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class TodoListTomorrowItemViewHolder(private val binding: RecyclerviewTodoListTomorrowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) {
            binding.todo = todo
        }
    }
}

object ItemDiffUtil : DiffUtil.ItemCallback<Todo>() {

    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}