package com.siuzannasmolianinova.notesmvi.data.local.mapper

import com.siuzannasmolianinova.notesmvi.data.local.NoteDtoModel
import com.siuzannasmolianinova.notesmvi.domain.NoteModel

fun NoteDtoModel.toNoteModel() = NoteModel(
    id = id,
    title = title,
    text = text,
    date = date
)

fun NoteModel.toNoteDtoModel() = NoteDtoModel(
    id = id,
    title = title,
    text = text,
    date = date
)
