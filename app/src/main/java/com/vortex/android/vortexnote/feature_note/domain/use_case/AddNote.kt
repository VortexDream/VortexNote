package com.vortex.android.vortexnote.feature_note.domain.use_case

import com.vortex.android.vortexnote.feature_note.domain.model.InvalidNoteContentException
import com.vortex.android.vortexnote.feature_note.domain.model.InvalidNoteTitleException
import com.vortex.android.vortexnote.feature_note.domain.model.Note
import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {


    @Throws(InvalidNoteTitleException::class, InvalidNoteContentException::class)
    suspend operator fun invoke(note: Note) {

        if(note.title.isBlank()) {
            throw InvalidNoteTitleException("Invalid note title")
        }

        if(note.content.isBlank()) {
            throw InvalidNoteContentException("Invalid note content")
        }

        repository.insertNote(note)
    }
}