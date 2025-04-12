package com.example.noteapptask6.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_list")
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L,

    @ColumnInfo(name = "note_title")
    var noteTitle: String = "",

    @ColumnInfo(name = "note_caption")
    var noteCaption: String = "",

    @ColumnInfo(name = "note_created_time")
    var createdTime: Long = System.currentTimeMillis()
)