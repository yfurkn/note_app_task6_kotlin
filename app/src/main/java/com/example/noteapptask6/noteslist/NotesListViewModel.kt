package com.example.noteapptask6.noteslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapptask6.database.NoteDatabaseDao
import com.example.noteapptask6.database.Notes
import kotlinx.coroutines.launch

class NotesListViewModel(
    private val database: NoteDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Notes>> = database.getAllNotes()

    fun deleteNote(note: Notes) {
        viewModelScope.launch {
            database.delete(note)
        }
    }
}