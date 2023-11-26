package com.vortex.android.vortexnote.feature_note.presentation.edit_note

import androidx.annotation.StringRes
import com.vortex.android.vortexnote.R

data class EditNoteState(
    val text: String = "",
    @StringRes val hint: Int = R.string.invalid_string,//Показывает ошибку, если в коде не заменить ссылку на строку
    val isHintVisible: Boolean = true
)
