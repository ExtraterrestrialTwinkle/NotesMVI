package com.siuzannasmolianinova.notesmvi.data.repo

import com.siuzannasmolianinova.notesmvi.data.local.NotesDao
import com.siuzannasmolianinova.notesmvi.data.local.mapper.toNoteDtoModel
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class NotesRepositoryImpl @Inject constructor(
    private val dao: NotesDao
) : NotesRepository {
    override fun getAllNotes() = dao.getAllNotes()

    override suspend fun saveNote(note: NoteModel) = dao.saveNote(note.toNoteDtoModel())

    override suspend fun deleteNote(id: Long) = dao.deleteNote(id)

    override suspend fun loadNote(id: Long) = dao.getNote(id)
}
