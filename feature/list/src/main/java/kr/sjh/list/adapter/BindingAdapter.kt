package kr.sjh.list.adapter

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.widget.TextView
import android.widget.TimePicker
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@BindingAdapter("app:isComplete")
fun isComplete(tv: TextView, isChecked: Boolean) {
    tv.apply {
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

@BindingAdapter(
    "app:adapter",
    "app:submitList",
    "app:itemDeco",
    requireAll = true
)
fun bindRecyclerView(
    rv: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    submitList: List<Any>?,
    deco: RecyclerView.ItemDecoration?,
) {
    rv.apply {
        this.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            (this as ListAdapter<Any, *>).submitList(submitList?.toMutableList())
        }
        deco?.let {
//            addItemDecoration(it)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}


