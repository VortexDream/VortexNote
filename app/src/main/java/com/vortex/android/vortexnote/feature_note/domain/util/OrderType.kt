package com.vortex.android.vortexnote.feature_note.domain.util

//Аналогично SortBy
//По убыванию / по возрастанию сортировка
sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
