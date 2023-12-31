package com.vortex.android.vortexnote.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NotesScreen: Screen("notes_screen")
    object EditNoteScreen: Screen("edit_note_screen")
}