package com.siuzannasmolianinova.notesmvi.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = NotesContract.TABLE_NAME)
data class NoteDtoModel(
    @PrimaryKey
    @ColumnInfo(name = NotesContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = NotesContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = NotesContract.Columns.TEXT)
    val text: String,
    @ColumnInfo(name = NotesContract.Columns.DATE)
    val date: LocalDateTime
)
