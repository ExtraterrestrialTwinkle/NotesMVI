package com.siuzannasmolianinova.notesmvi.data.repo

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository

class NotesRepositoryImpl : NotesRepository {
    override suspend fun getAllNotes(): List<NoteModel> {
        // TODO: implement receiving of all notes from db
        return emptyList()
    }

    override suspend fun saveNote(note: NoteModel) {
        // TODO: implement saving note in db
    }

    override suspend fun deleteNote(id: Long) {
        // TODO: implement deleting note by id from db
    }
}
