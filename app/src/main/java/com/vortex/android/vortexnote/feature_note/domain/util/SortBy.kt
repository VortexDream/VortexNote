package com.vortex.android.vortexnote.feature_note.domain.util

//Sealed class, похож на энумы, за исключением того, что подклассы обладают стейтом
//Нужен для более лаконичного кода, когда дело касается оператора when
//Сортировка по различным параметрам
sealed class SortBy(val orderType: OrderType) {

    class Title(orderType: OrderType): SortBy(orderType)
    class Date(orderType: OrderType): SortBy(orderType)
    class Color(orderType: OrderType): SortBy(orderType)

    fun copy(orderType: OrderType): SortBy {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}