package com.siuzannasmolianinova.notesmvi.domain.usecase

import com.siuzannasmolianinova.notesmvi.data.local.mapper.toNoteModel
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import javax.inject.Inject

class LoadNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseUseCase<NoteModel?, Long>() {
    override suspend fun run(args: Long): NoteModel {
        return notesRepository.loadNote(args).toNoteModel()
    }
}
