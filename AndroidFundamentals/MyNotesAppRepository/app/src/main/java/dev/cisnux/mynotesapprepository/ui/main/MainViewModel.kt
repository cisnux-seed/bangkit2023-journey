package dev.cisnux.mynotesapprepository.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.cisnux.mynotesapprepository.database.Note
import dev.cisnux.mynotesapprepository.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)
    val notes: LiveData<List<Note>> get() = mNoteRepository.getAllNotes()
}