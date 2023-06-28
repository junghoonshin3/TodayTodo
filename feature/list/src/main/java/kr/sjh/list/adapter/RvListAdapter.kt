package kr.sjh.list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.domain.model.ListViewType.EMPTY
import kr.sjh.domain.model.ListViewType.HEADER_TODAY
import kr.sjh.domain.model.ListViewType.HEADER_TOMMOROW
import kr.sjh.domain.model.ListViewType.ITEM
import kr.sjh.domain.model.ListViewType.ITEM_TOMORROW
import kr.sjh.domain.model.Todo
import kr.sjh.list.ListViewModel
import kr.sjh.list.databinding.*
import kr.sjh.list.listener.ItemClickListener

class RvListAdapter(private val listener: ItemClickListener) :
    ListAdapter<Todo, RecyclerView.ViewHolder>(ItemDiffUtil) {


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
            binding.listener = listener
            binding.cbCheck.apply {
                //마음에안드네.. databinding 으로 해결가능한데 현재 databinding 시 observablefild를 사용할수없어서 문제..

                isChecked = todo.is_check
                complete(todo.is_check, binding.tvItemList)

                setOnCheckedChangeListener { buttonView, isChecked ->
                    todo.is_check = isChecked
                    listener.onCheckBoxClick(todo, isChecked)
                    complete(isChecked, binding.tvItemList)
                }
            }
        }

        fun complete(isChecked: Boolean, tv: TextView) {
            with(tv) {
                if (isChecked) {
                    paintFlags =
                        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(Color.GRAY)
                } else {
                    paintFlags =
                        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setTextColor(Color.BLACK)
                }
            }
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
            binding.executePendingBindings()
        }
    }
}

object ItemDiffUtil : DiffUtil.ItemCallback<Todo>() {

    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}