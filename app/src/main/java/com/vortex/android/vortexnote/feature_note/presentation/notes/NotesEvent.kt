package com.vortex.android.vortexnote.feature_note.presentation.notes

import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.util.SortBy

//Все события, которые могут произойти в интерфейсе
sealed class NotesEvent {
    data class Order(val sortBy: SortBy): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}
