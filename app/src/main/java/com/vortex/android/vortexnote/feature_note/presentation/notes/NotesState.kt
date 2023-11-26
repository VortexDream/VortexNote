package com.vortex.android.vortexnote.feature_note.presentation.notes

import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.util.OrderType
import com.vortex.android.vortexnote.feature_note.domain.util.SortBy

//Дата класс для сохранения состояния интерфейса во ViewModel
data class NotesState(
    val notes: List<Note> = emptyList(),
    val sortBy: SortBy = SortBy.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
