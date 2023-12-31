package com.vortex.android.vortexnote.feature_note.domain.use_case

import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository

//Фича удаления заметки из базы данных
class DeleteNote(
    private val repository: NoteRepository
) {

    //invoke() и простой вызов функции равноценны
    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}