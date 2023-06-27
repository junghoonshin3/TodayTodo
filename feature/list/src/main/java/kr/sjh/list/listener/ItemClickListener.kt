package kr.sjh.list.listener

import android.widget.CompoundButton
import androidx.databinding.ObservableField
import kotlinx.coroutines.flow.MutableSharedFlow
import kr.sjh.domain.model.Todo

interface ItemClickListener {
    fun onItemClick(todo: Todo)

}