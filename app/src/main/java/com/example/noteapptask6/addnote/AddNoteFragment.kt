package com.example.noteapptask6.addnote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapptask6.R
import com.example.noteapptask6.database.NoteDatabase
import com.example.noteapptask6.databinding.AddNoteFragmentBinding

class AddNoteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: AddNoteFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.add_note_fragment, container, false)

        // TAKE DAO
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao

        // ViewModel + Factory
        val viewModelFactory = AddNoteViewModelFactory(dataSource, application)
        val addNoteViewModel = ViewModelProvider(this, viewModelFactory)[AddNoteViewModel::class.java]

        // binding
        binding.addNoteViewModel = addNoteViewModel
        binding.lifecycleOwner = viewLifecycleOwner


        binding.confirmNoteButton.setOnClickListener {
            val title   = binding.titleEditText.text.toString()
            val caption = binding.captionEditText.text.toString()

            addNoteViewModel.onSaveNote(title, caption)
            findNavController().navigate(
                AddNoteFragmentDirections
                    .actionAddNoteFragmentToNotesListFragment()
            )
        }

        return binding.root
    }
}