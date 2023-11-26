package com.vortex.android.vortexnote.feature_note.domain.repository

import com.vortex.android.vortexnote.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

//Причина, по которой для этого испльзуется интерфейс, а не обычный класс - для того чтобы иметь
//Возмжнсть создавать фейковые репзитрии для целей тестировании (условно NoteRepositoryImpl -
//Настоящий, а для тестирования будет создан другой репозиторий, наследующий от этого интерфейса
interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}