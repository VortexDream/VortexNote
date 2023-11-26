package com.vortex.android.vortexnote.di

import android.app.Application
import androidx.room.Room
import com.vortex.android.vortexnote.feature_note.data.data_source.NoteDatabase
import com.vortex.android.vortexnote.feature_note.data.repository.NoteRepositoryImpl
import com.vortex.android.vortexnote.feature_note.domain.repository.NoteRepository
import com.vortex.android.vortexnote.feature_note.domain.use_case.AddNote
import com.vortex.android.vortexnote.feature_note.domain.use_case.DeleteNote
import com.vortex.android.vortexnote.feature_note.domain.use_case.GetNote
import com.vortex.android.vortexnote.feature_note.domain.use_case.GetNotes
import com.vortex.android.vortexnote.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Модуль для Даггер Хилт, здесь репозиторий передается в юзкейсы, котрые можно будет легко вызвать
//в нужном месте
@Module
@InstallIn(SingletonComponent::class)//Где будут использваться экземпляры из этого модуля, см. иерархию в документации
object AppModule {

    @Provides
    @Singleton//Определяет логику создания экземпляров, в данном случае - один экземпляр на всю программу
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
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}