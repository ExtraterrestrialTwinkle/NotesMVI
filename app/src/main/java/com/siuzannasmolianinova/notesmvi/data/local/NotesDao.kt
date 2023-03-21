package com.siuzannasmolianinova.notesmvi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note: NoteDtoModel)

    @Upsert
    fun saveChanges(note: NoteDtoModel)

    @Query("SELECT * FROM ${NotesContract.TABLE_NAME} WHERE ${NotesContract.Columns.ID} = :id")
    fun getNote(id: Long): NoteDtoModel

    @Query("SELECT * FROM ${NotesContract.TABLE_NAME}")
    fun getAllNotes(): Flow<List<NoteDtoModel>>

    @Query("DELETE FROM ${NotesContract.TABLE_NAME} WHERE ${NotesContract.Columns.ID} = :id")
    fun deleteNote(id: Long)
}
