package com.vortex.android.vortexnote.di

import android.app.Application
import androidx.room.Room
import com.vortex.android.vortexnote.feature_note.data.data_source.NoteDatabase
import com.vortex.android.vortexnote.feature_note.data.repository.NoteRepositoryImpl
import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository
import com.vortex.android.vortexnote.feature_note.domain.use_case.DeleteNote
import com.vortex.android.vortexnote.feature_note.domain.use_case.GetNotes
import com.vortex.android.vortexnote.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(database: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(database.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}