package com.siuzannasmolianinova.notesmvi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.siuzannasmolianinova.notesmvi.data.local.NotesDatabase.Companion.DB_VERSION

@TypeConverters(LocalDateTimeConverter::class)
@Database(
    entities = [NoteDtoModel::class],
    version = DB_VERSION
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "notes_database"

//        lateinit var instance: NotesDatabase
//            private set

//        fun init(context: Context) {
//            instance = Room.databaseBuilder(
//                context,
//                NotesDatabase::class.java,
//                DB_NAME
//            )
//                .build()
//        }
    }
}
