package com.vortex.android.vortexnote.feature_note.domain.util

sealed class SortBy(val orderType: OrderType) {

    class Title(orderType: OrderType): SortBy(orderType)

    class Date(orderType: OrderType): SortBy(orderType)

    class Color(orderType: OrderType): SortBy(orderType)
}