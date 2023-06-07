package kr.sjh.list.listener

import kr.sjh.domain.model.Todo

interface ItemClickListener {
    fun onItemClick(todo: Todo, isClick: Boolean)
}