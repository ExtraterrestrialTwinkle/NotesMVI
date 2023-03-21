package com.siuzannasmolianinova.notesmvi.di

import android.app.Application
import androidx.room.Room
import com.siuzannasmolianinova.notesmvi.data.local.NotesDao
import com.siuzannasmolianinova.notesmvi.data.local.NotesDatabase
import com.siuzannasmolianinova.notesmvi.data.repo.NotesRepositoryImpl
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    fun providesRepository(notesDao: NotesDao): NotesRepository = NotesRepositoryImpl(notesDao)
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(application: Application): NotesDatabase {
        return Room.databaseBuilder(
            application,
            NotesDatabase::class.java,
            NotesDatabase.DB_NAME
        )
            .build()
    }

    @Provides
    fun providesNotesDao(db: NotesDatabase): NotesDao {
        return db.notesDao()
    }
}
