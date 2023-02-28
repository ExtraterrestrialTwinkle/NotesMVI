package com.siuzannasmolianinova.notesmvi.domain.repository

import com.siuzannasmolianinova.notesmvi.domain.NoteModel

interface NotesRepository {
    suspend fun getAllNotes(): List<NoteModel>
    suspend fun saveNote(note: NoteModel)
    suspend fun deleteNote(id: Long)
}