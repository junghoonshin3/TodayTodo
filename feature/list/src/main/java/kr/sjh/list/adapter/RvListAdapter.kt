package kr.sjh.list.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.domain.model.Item
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
    ListAdapter<Item, RecyclerView.ViewHolder>(ItemDiffUtil) {

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

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType

    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    inner class TodoListItemViewHolder(private val binding: RecyclerviewTodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Item) = with(binding) {
            when (todo) {
                is Todo -> {
                    this.todo = todo
                    val item = tvItemList
                    cbCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {
                            item.paintFlags =
                                item.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                            item.setTextColor(Color.GRAY)
                        } else {
                            item.paintFlags =
                                item.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                            item.setTextColor(Color.BLACK)
                        }
                        listener.onItemClick(todo, isChecked)
                    }
                }
            }

            executePendingBindings()
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
        fun bind(todo: Item) {
            when (todo) {
                is Todo -> binding.todo = todo
            }
        }
    }
}

object ItemDiffUtil : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}