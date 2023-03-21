package com.siuzannasmolianinova.notesmvi.domain.repository

import com.siuzannasmolianinova.notesmvi.data.local.NoteDtoModel
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<NoteDtoModel>>
    suspend fun saveNote(note: NoteModel)
    suspend fun deleteNote(id: Long)
    suspend fun loadNote(id: Long): NoteDtoModel
}