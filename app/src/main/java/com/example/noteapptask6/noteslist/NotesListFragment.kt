package com.example.noteapptask6.noteslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.noteapptask6.R
import com.example.noteapptask6.database.NoteDatabase
import com.example.noteapptask6.database.Notes
import com.example.noteapptask6.databinding.NotesListFragmentBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotesListFragment : Fragment() {

    private lateinit var binding: NotesListFragmentBinding
    private lateinit var viewModel: NotesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.notes_list_fragment, container, false)

        /* ViewModel + DAO */
        val application = requireNotNull(activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDatabaseDao
        val factory = NotesListViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, factory)[NotesListViewModel::class.java]

        binding.noteListViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(NotesListFragmentDirections.actionNotesListFragmentToAddNoteFragment())
        }

        /* Notları gözlemle ve UI’yi yenile */
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            populateNotes(notes)
        }

        return binding.root
    }

    private fun populateNotes(notes: List<Notes>) {
        val container: LinearLayout = binding.notesContainer
        container.removeAllViews()          // önceki elemanları temizle
        val inflater = LayoutInflater.from(requireContext())
        val dateFmt = SimpleDateFormat("dd MMM yyyy • HH:mm", Locale.getDefault())

        notes.forEach { note ->
            /* Basit kart benzeri dikey LinearLayout */
            val card = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(24)
                // İsterseniz arka plan/çerçeve ekleyebilirsiniz
            }

            val titleTv = TextView(requireContext()).apply {
                text = note.noteTitle.ifBlank { getString(R.string.no_title) }
                textSize = 18f
                setTypeface(typeface, android.graphics.Typeface.BOLD)
            }

            val captionTv = TextView(requireContext()).apply {
                text = note.noteCaption
                textSize = 16f
            }

            val timeTv = TextView(requireContext()).apply {
                text = dateFmt.format(Date(note.createdTime))
                textSize = 12f
            }

            val deleteBtn = Button(requireContext()).apply {
                text = getString(R.string.delete)
                setOnClickListener { viewModel.deleteNote(note) }
            }

            /* Görünümleri sırayla karta ekle */
            card.addView(titleTv)
            card.addView(captionTv)
            card.addView(timeTv)
            card.addView(deleteBtn)

            /* Kartı ana konteynıra ekle */
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { setMargins(0, 0, 0, 24) }
            container.addView(card, lp)
        }
    }
}