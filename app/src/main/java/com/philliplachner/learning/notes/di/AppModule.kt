package com.philliplachner.learning.notes.di

import android.app.Application
import androidx.navigation.Navigator
import androidx.room.Room
import com.philliplachner.learning.notes.feature_note.data.data_source.NoteDatabase
import com.philliplachner.learning.notes.feature_note.data.repository.NoteRepositoryImpl
import com.philliplachner.learning.notes.feature_note.domain.NoteRepository
import com.philliplachner.learning.notes.feature_note.domain.use_case.DeleteNote
import com.philliplachner.learning.notes.feature_note.domain.use_case.GetNotes
import com.philliplachner.learning.notes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}

