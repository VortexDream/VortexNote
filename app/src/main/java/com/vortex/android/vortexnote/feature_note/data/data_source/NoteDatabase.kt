package com.vortex.android.vortexnote.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vortex.android.vortexnote.feature_note.domain.model.Note

//Room, база данных заметок на основе класса Note
@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "NOTES_DB"
    }
}