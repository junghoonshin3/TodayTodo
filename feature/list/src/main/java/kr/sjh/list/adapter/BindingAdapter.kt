package kr.sjh.list.adapter

import android.graphics.Color
import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:isComplete")
fun TextView.isComplete(isChecked: Boolean) {
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
