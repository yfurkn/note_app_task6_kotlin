package com.example.noteapptask6.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

interface NoteDatabaseDao {

    @Insert
    suspend fun insert(note: Notes)

    @Update
    suspend fun update(note: Notes)

    @Delete
    suspend fun delete(note: Notes)

    @Query("Select * from notes_list WHERE noteId = :key")
    suspend fun get(key: Long): Notes?

    @Query("Select * from notes_list ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Notes>>
}