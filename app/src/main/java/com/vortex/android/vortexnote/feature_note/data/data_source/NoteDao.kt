package com.vortex.android.vortexnote.feature_note.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vortex.android.vortexnote.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

//Интерфейс для доступе к базе данных
@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")//Получение списка заметок
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")//Получение заметки по id
    suspend fun getNote(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)//При конфликте заменить
    suspend fun addNote(note: Note)//Добавление заметки

    @Delete
    suspend fun deleteNote(note: Note)//Удалить заметку
}