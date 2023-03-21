package com.siuzannasmolianinova.notesmvi.domain.usecase

import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseUseCase<Unit, NoteModel>() {
    override suspend fun run(args: NoteModel) = notesRepository.saveNote(args)
}
