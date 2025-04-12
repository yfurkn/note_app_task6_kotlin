package com.example.noteapptask6.addnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapptask6.database.NoteDatabaseDao
import com.example.noteapptask6.database.Notes
import kotlinx.coroutines.launch

class AddNoteViewModel(private val database: NoteDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    fun onSaveNote(title: String, caption: String) {
        if (title.isBlank() && caption.isBlank()) return

        val newNote = Notes(
            noteTitle = title,
            noteCaption = caption,
            createdTime = System.currentTimeMillis()
        )

        viewModelScope.launch {
            database.insert(newNote)
        }
    }
}