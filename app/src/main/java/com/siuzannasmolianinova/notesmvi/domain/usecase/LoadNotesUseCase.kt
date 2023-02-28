package com.siuzannasmolianinova.notesmvi.domain.usecase

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import javax.inject.Inject

class LoadNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseUseCase<List<NoteModel>>() {

    override suspend fun invoke(): List<NoteModel> = notesRepository.getAllNotes()
}
