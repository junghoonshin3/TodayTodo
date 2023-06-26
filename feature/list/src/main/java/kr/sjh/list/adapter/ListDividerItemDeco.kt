package kr.sjh.list.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.sjh.domain.model.ListViewType


class ListDividerItemDeco : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position < 0) {
            return
        }
        val viewType = parent.adapter?.getItemViewType(position)

        when (viewType) {
            ListViewType.HEADER_TODAY -> {
                outRect.left = 5f.dp2px
                outRect.right = 5f.dp2px
                outRect.bottom = 5f.dp2px
            }
            ListViewType.HEADER_TOMMOROW -> {
                outRect.left = 5f.dp2px
                outRect.right = 5f.dp2px
                outRect.bottom = 5f.dp2px
                outRect.top = 5f.dp2px
            }
            ListViewType.ITEM, ListViewType.ITEM_TOMORROW -> {
                outRect.left = 5f.dp2px
                outRect.top = 2f.dp2px
                outRect.bottom = 2f.dp2px
            }
        }

    }


}