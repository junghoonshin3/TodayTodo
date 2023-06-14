package kr.sjh.list.adapter

import android.graphics.Rect
import android.util.Log
import android.view.View
import android.widget.HeaderViewListAdapter
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView


class ListDividerItemDeco constructor(
    private val left: Int,
    private val right: Int,
    private val top: Int,
    private val bottom: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        Log.i("sjh","${parent.adapter?.itemCount}")
    }


}