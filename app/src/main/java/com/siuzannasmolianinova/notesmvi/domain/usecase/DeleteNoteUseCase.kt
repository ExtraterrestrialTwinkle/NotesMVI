package com.siuzannasmolianinova.notesmvi.domain.usecase

import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseUseCase<Unit, Long>() {
    override suspend fun run(args: Long) = notesRepository.deleteNote(args)
}
