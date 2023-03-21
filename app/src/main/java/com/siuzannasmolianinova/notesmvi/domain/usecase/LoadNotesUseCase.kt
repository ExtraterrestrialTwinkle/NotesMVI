package com.siuzannasmolianinova.notesmvi.domain.usecase

import android.util.Log
import com.siuzannasmolianinova.notesmvi.data.local.mapper.toNoteModel
import com.siuzannasmolianinova.notesmvi.domain.NoteModel
import com.siuzannasmolianinova.notesmvi.domain.repository.NotesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class LoadNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository
) : FlowUseCase<List<NoteModel>>() {
    override fun performAction(): Flow<Result<List<NoteModel>>> =
        notesRepository.getAllNotes().map { list ->
            Result.success(list.map { it.toNoteModel() })
        }
}
