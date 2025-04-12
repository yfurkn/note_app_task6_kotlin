package com.example.noteapptask6.noteslist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapptask6.database.NoteDatabaseDao

class NotesListViewModelFactory(
    private val dataSource: NoteDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}