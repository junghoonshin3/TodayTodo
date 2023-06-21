package kr.sjh.domain.model

abstract class Item(
    val id: Int = 0,
    var viewType: Int = ListViewType.ITEM
)