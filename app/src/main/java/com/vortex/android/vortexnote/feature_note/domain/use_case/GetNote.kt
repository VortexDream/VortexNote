package com.vortex.android.vortexnote.feature_note.domain.use_case

import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {

    //invoke() и простой вызов функции равноценны
    suspend operator fun invoke(id: Int) = repository.getNote(id)
}