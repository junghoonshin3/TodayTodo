package kr.sjh.domain.model

object ListViewType {
    const val HEADER_TODAY = 0 // 오늘 헤더 뷰
    const val HEADER_TOMMOROW = 1 // 내일 헤더 뷰
    const val ITEM = 2 // 리사이클러 아이템 뷰
    const val EMPTY = 3 // 데이터가 없을 때 뜨는 뷰
    const val ITEM_TOMORROW = 4 // 리사이클러 내일 아이템 뷰
}