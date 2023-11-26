package com.vortex.android.vortexnote.feature_note.presentation.edit_note

import androidx.compose.ui.focus.FocusState

sealed class EditNoteEvent{
    data class EnteredTitle(val text: String): EditNoteEvent()
    data class EnteredContent(val text: String): EditNoteEvent()
    data class FocusEditTitle(val focusState: FocusState): EditNoteEvent()
    data class FocusEditContent(val focusState: FocusState): EditNoteEvent()
    data class ChangeColor(val color: Int): EditNoteEvent()
    object SaveNote: EditNoteEvent()
}
