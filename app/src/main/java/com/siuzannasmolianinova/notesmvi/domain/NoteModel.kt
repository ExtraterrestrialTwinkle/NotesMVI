package com.siuzannasmolianinova.notesmvi.domain

import java.time.LocalDateTime

data class NoteModel(
    val id: Long,
    val title: String,
    val text: String = "",
    val date: LocalDateTime
)
