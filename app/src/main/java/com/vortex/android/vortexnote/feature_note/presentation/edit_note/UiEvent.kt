package com.vortex.android.vortexnote.feature_note.presentation.edit_note

import androidx.annotation.StringRes

//Класс для использования в EditNoteViewModel в SharedFlow
sealed class UiEvent {
    data class ShowSnackbar(@StringRes val messageInt: Int): UiEvent()
    object SaveNote: UiEvent()
}